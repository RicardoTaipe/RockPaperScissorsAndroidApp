package com.example.rockpaperscissorsapp.data

import com.example.rockpaperscissorsapp.utils.combine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

private const val ONE_SECOND = 1000L

class RockPaperScissorsEngine(
    private val dispatcher: CoroutineDispatcher
) : RockPaperScissorsController {

    private val scope = CoroutineScope(dispatcher)

    private val _gameState = MutableStateFlow(GameState.IDLE)
    private val _playerMove = MutableStateFlow<Move?>(null)
    private val _opponentMove = MutableStateFlow<Move?>(null)
    private val _rounds = MutableStateFlow<List<Round>>(emptyList())
    private val _score = MutableStateFlow(Score())
    private val _countdown = MutableStateFlow<Int?>(null)

    private val _uiState = MutableStateFlow(GameUiState())

    override val gameState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Delegates
    override var playerMove: Move? by _playerMove
    override var opponentMove: Move? by _opponentMove

    override val rounds: List<Round>
        get() = _rounds.value

    private var countdownJob: Job? = null

init {
    scope.launch {
        combine(
            _gameState,
            _playerMove,
            _opponentMove,
            _rounds,
            _score,
            _countdown
        ) { gameState, playerMove, opponentMove, rounds, score, countdown ->
            GameUiState(
                state = gameState,
                playerMove = playerMove,
                opponentMove = opponentMove,
                rounds = rounds,
                score = score,
                countdown = countdown
            )
        }.catch {
            throw it
        }.collect {
            _uiState.value = it
        }
    }
}

    override fun startGame() {
        _rounds.value = emptyList()
        _score.value = Score()
        _gameState.value = GameState.WAITING_FOR_PLAYER
    }

    override fun play(move: Move) {
        if (_gameState.value != GameState.WAITING_FOR_PLAYER) return

        playerMove = move
        _gameState.value = GameState.WAITING_FOR_OPPONENT

        startCountdown()
    }

    private fun startCountdown() {
        countdownJob?.cancel()

        countdownJob = scope.launch {
            for (i in 3 downTo 1) {
                _countdown.value = i
                delay(ONE_SECOND)
            }

            _countdown.value = null
            generateOpponentMove()
        }
    }

    override fun generateOpponentMove() {
        opponentMove = Move.values().random()
        evaluateRound()
    }

    override fun evaluateRound() {
        val player = playerMove ?: return
        val opponent = opponentMove ?: return

        val result = determineResult(player, opponent)

        val round = Round(player, opponent, result)

        _rounds.update { it + round }
        _score.update { it.add(result) }

        _gameState.value = GameState.RESULT
    }

    override fun nextRound() {
        countdownJob?.cancel()
        countdownJob = null

        playerMove = null
        opponentMove = null
        _countdown.value = null

        _gameState.value = GameState.WAITING_FOR_PLAYER
    }

    override fun resetGame() {
        countdownJob?.cancel()
        countdownJob = null

        playerMove = null
        opponentMove = null
        _countdown.value = null
        _rounds.value = emptyList()
        _score.value = Score()
        _gameState.value = GameState.IDLE
    }

    private fun determineResult(player: Move, opponent: Move): GameResult {
        return when {
            player == opponent -> GameResult.DRAW
            player == Move.ROCK && opponent == Move.SCISSORS -> GameResult.WIN
            player == Move.PAPER && opponent == Move.ROCK -> GameResult.WIN
            player == Move.SCISSORS && opponent == Move.PAPER -> GameResult.WIN
            else -> GameResult.LOSE
        }
    }
}

private operator fun <T> MutableStateFlow<T>.setValue(
    thisObj: Any?,
    property: KProperty<*>,
    value: T
) {
    this.value = value
}

private operator fun <T> MutableStateFlow<T>.getValue(
    thisObj: Any?,
    property: KProperty<*>
): T = this.value