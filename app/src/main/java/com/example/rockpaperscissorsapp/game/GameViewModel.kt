package com.example.rockpaperscissorsapp.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rockpaperscissorsapp.data.GameResult
import com.example.rockpaperscissorsapp.data.Move
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val ONE_SECOND = 1000L
private const val COUNTDOWN_START = 3

data class GameScreenState(
    val game: GameUiState = GameUiState.WaitingForPlayer(),
    val isRulesDialogOpen: Boolean = false
)

sealed class GameUiState {
    abstract val score: Int

    data class WaitingForPlayer(override val score: Int = 0) : GameUiState()

    data class WaitingForOpponent(
        val playerMove: Move,
        val countdown: Int?,
        override val score: Int,
    ) : GameUiState()

    data class Result(
        val playerMove: Move,
        val opponentMove: Move,
        val gameResult: GameResult,
        override val score: Int,
    ) : GameUiState()
}

sealed class GameIntent {
    data class Play(val move: Move) : GameIntent()
    object NextRound : GameIntent()
    object ResetGame : GameIntent()
    object OpenRules : GameIntent()
    object CloseRules : GameIntent()
}


class GameViewModel(private val randomGenerator: () -> Move) : ViewModel() {

    private val _state = MutableStateFlow(GameScreenState())
    val state = _state.asStateFlow()
    private var countdownJob: Job? = null

    fun process(intent: GameIntent) {
        when (intent) {
            is GameIntent.Play -> play(intent.move)
            is GameIntent.NextRound -> nextRound()
            is GameIntent.ResetGame -> resetGame()
            GameIntent.OpenRules -> _state.update { it.copy(isRulesDialogOpen = true) }
            GameIntent.CloseRules -> _state.update { it.copy(isRulesDialogOpen = false) }
        }
    }

    private fun play(move: Move) {
        _state.update { current ->
            if (current.game !is GameUiState.WaitingForPlayer) return@update current

            current.copy(
                game = GameUiState.WaitingForOpponent(
                    playerMove = move,
                    countdown = COUNTDOWN_START,
                    score = current.game.score
                )
            )
        }
        startCountdown()
    }

    private fun startCountdown() {
        countdownJob?.cancel()

        countdownJob = viewModelScope.launch {
            for (i in COUNTDOWN_START downTo 1) {
                _state.update { current ->
                    val game = current.game
                    if (game is GameUiState.WaitingForOpponent) {
                        current.copy(game = game.copy(countdown = i))
                    } else {
                        current
                    }
                }
                delay(ONE_SECOND)
            }
            generateOpponentMove()
        }
    }

    private fun calculateNewScore(currentScore: Int, result: GameResult): Int = when (result) {
        GameResult.WIN -> currentScore + 1
        GameResult.LOSE -> (currentScore - 1).coerceAtLeast(0)
        GameResult.DRAW -> currentScore
    }


    private fun generateOpponentMove() {
        val opponentMove = randomGenerator.invoke()
        _state.update { current ->
            val game = current.game
            if (game !is GameUiState.WaitingForOpponent) return@update current
            val result = game.playerMove.compare(opponentMove)

            current.copy(
                game = GameUiState.Result(
                    playerMove = game.playerMove,
                    opponentMove = opponentMove,
                    gameResult = result,
                    score = calculateNewScore(game.score, result)
                )
            )
        }
    }

    private fun nextRound() {
        countdownJob?.cancel()
        _state.update { it.copy(game = GameUiState.WaitingForPlayer(score = it.game.score)) }
    }

    private fun resetGame() {
        countdownJob?.cancel()
        _state.value = GameScreenState()
    }
}