package com.example.rockpaperscissorsapp.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface RockPaperScissorsController {

    val gameState: StateFlow<GameUiState>

    var playerMove: Move?
    var opponentMove: Move?

    val rounds: List<Round>

    fun startGame()
    fun play(move: Move)
    fun generateOpponentMove()
    fun evaluateRound()
    fun nextRound()
    fun resetGame()
}

data class GameUiState(
    val state: GameState = GameState.IDLE,
    val playerMove: Move? = null,
    val opponentMove: Move? = null,
    val rounds: List<Round> = emptyList(),
    val score: Score = Score(),
    val countdown: Int? = null
)

data class Score(
    val wins: Int = 0,
    val losses: Int = 0,
    val draws: Int = 0
) {
    fun add(result: GameResult): Score {
        return when (result) {
            GameResult.WIN -> copy(wins = wins + 1)
            GameResult.LOSE -> copy(losses = losses + 1)
            GameResult.DRAW -> copy(draws = draws + 1)
        }
    }
}

data class Round(
    val playerMove: Move,
    val opponentMove: Move,
    val result: GameResult
)

enum class Move { ROCK, PAPER, SCISSORS }

enum class GameResult { WIN, LOSE, DRAW }

enum class GameState {
    IDLE,
    WAITING_FOR_PLAYER,
    WAITING_FOR_OPPONENT,
    RESULT
}

// MVI ARCHITECTURE ------------
sealed class GameIntent {
    object StartGame : GameIntent()
    data class Play(val move: Move) : GameIntent()
    object NextRound : GameIntent()
    object ResetGame : GameIntent()
}

class RockPaperScissorsMVI(
    private val dispatcher: CoroutineDispatcher
) {

    private val scope = CoroutineScope(dispatcher)

    private val _state = MutableStateFlow(GameUiState())
    val state: StateFlow<GameUiState> = _state.asStateFlow()

    private var countdownJob: Job? = null

    fun process(intent: GameIntent) {
        when (intent) {
            is GameIntent.StartGame -> startGame()
            is GameIntent.Play -> play(intent.move)
            is GameIntent.NextRound -> nextRound()
            is GameIntent.ResetGame -> resetGame()
        }
    }

    private fun startGame() {
        _state.update {
            it.copy(
                state = GameState.WAITING_FOR_PLAYER,
                rounds = emptyList(),
                score = Score(),
                playerMove = null,
                opponentMove = null,
                countdown = null
            )
        }
    }

    private fun play(move: Move) {
        val current = _state.value
        if (current.state != GameState.WAITING_FOR_PLAYER) return

        _state.update {
            it.copy(
                playerMove = move,
                state = GameState.WAITING_FOR_OPPONENT
            )
        }

        startCountdown()
    }

    private fun startCountdown() {
        countdownJob?.cancel()

        countdownJob = scope.launch {
            for (i in 3 downTo 1) {
                _state.update { it.copy(countdown = i) }
                delay(1000)
            }

            _state.update { it.copy(countdown = null) }

            generateOpponentMove()
        }
    }

    private fun generateOpponentMove() {
        val opponentMove = Move.values().random()

        _state.update {
            it.copy(opponentMove = opponentMove)
        }

        evaluateRound()
    }

    private fun evaluateRound() {
        val current = _state.value
        val player = current.playerMove ?: return
        val opponent = current.opponentMove ?: return

        val result = determineResult(player, opponent)

        val round = Round(player, opponent, result)

        _state.update {
            it.copy(
                rounds = it.rounds + round,
                score = it.score.add(result),
                state = GameState.RESULT
            )
        }
    }

    private fun nextRound() {
        countdownJob?.cancel()

        _state.update {
            it.copy(
                playerMove = null,
                opponentMove = null,
                countdown = null,
                state = GameState.WAITING_FOR_PLAYER
            )
        }
    }

    private fun resetGame() {
        countdownJob?.cancel()

        _state.value = GameUiState()
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

/***

@Composable
fun GameScreen(viewModel: RockPaperScissorsViewModel) {

val state by viewModel.uiState.collectAsState()

when (state.state) {
GameState.WAITING_FOR_PLAYER -> {
Button(onClick = { viewModel.play(Move.ROCK) }) {
Text("Rock")
}
}

GameState.WAITING_FOR_OPPONENT -> {
Text("Thinking... ${state.countdown ?: ""}")
}

GameState.RESULT -> {
Text("Result: ${state.rounds.lastOrNull()?.result}")
Button(onClick = { viewModel.nextRound() }) {
Text("Next Round")
}
}

else -> {
Button(onClick = { viewModel.startGame() }) {
Text("Start Game")
}
}
}
}
 */