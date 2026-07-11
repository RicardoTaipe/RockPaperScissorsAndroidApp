package com.example.rockpaperscissorsapp

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rockpaperscissorsapp.data.GameResult
import com.example.rockpaperscissorsapp.data.Move
import com.example.rockpaperscissorsapp.game.GameIntent
import com.example.rockpaperscissorsapp.game.GameScreen
import com.example.rockpaperscissorsapp.game.GameScreenState
import com.example.rockpaperscissorsapp.game.GameUiState
import com.example.rockpaperscissorsapp.game.GameViewModel
import com.example.rockpaperscissorsapp.game.GameViewModelFactory
import com.example.rockpaperscissorsapp.play.PlayGameScreen
import com.example.rockpaperscissorsapp.result.ResultScreen
import com.example.rockpaperscissorsapp.rules.RulesScreen
import com.example.rockpaperscissorsapp.ui.components.Header
import com.example.rockpaperscissorsapp.ui.components.RulesButton
import com.example.rockpaperscissorsapp.ui.theme.MyApplicationTheme
import com.example.rockpaperscissorsapp.ui.theme.largeRadialGradient

@Composable
fun RockPaperScissorsApp(
    gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory)
) {
    val uiState by gameViewModel.state.collectAsStateWithLifecycle()

    RockPaperScissorsContent(uiState = uiState) {
        gameViewModel.process(intent = it)
    }
}

@Composable
fun RockPaperScissorsContent(
    modifier: Modifier = Modifier,
    uiState: GameScreenState,
    intent: (GameIntent) -> Unit = {}
) {
    BackHandler(enabled = uiState.game is GameUiState.WaitingForOpponent || uiState.game is GameUiState.Result) {
        intent(GameIntent.NextRound)
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            Header(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
                score = uiState.game.score.toString()
            )
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                RulesButton { intent(GameIntent.OpenRules) }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(brush = largeRadialGradient)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AnimatedContent(
                targetState = uiState.game,
                contentKey = {
                    when (it) {
                        is GameUiState.WaitingForPlayer -> "play"
                        is GameUiState.WaitingForOpponent -> "waiting"
                        is GameUiState.Result -> "result"
                    }
                }
            ) { gameState ->
                when (gameState) {
                    is GameUiState.WaitingForPlayer -> PlayGameScreen { intent(GameIntent.Play(move = it)) }
                    is GameUiState.WaitingForOpponent -> GameScreen(uiState = gameState)
                    is GameUiState.Result -> ResultScreen(uiState = gameState) {
                        intent(GameIntent.NextRound)
                    }
                }
            }

            if (uiState.isRulesDialogOpen) {
                RulesScreen { intent(GameIntent.CloseRules) }
            }
        }
    }
}

private class GameScreenStatePreviewProvider : PreviewParameterProvider<GameScreenState> {
    override val values = sequenceOf(
        // PlayerPreview
        GameScreenState(
            GameUiState.WaitingForPlayer(0)
        ),
        // OpponentPreview
        GameScreenState(
            GameUiState.WaitingForOpponent(
                playerMove = Move.ROCK,
                countdown = 2,
                score = 1
            )
        ),
        // ResultPreview
        GameScreenState(
            GameUiState.Result(
                playerMove = Move.PAPER,
                opponentMove = Move.ROCK,
                gameResult = GameResult.WIN,
                score = 1
            )
        ),
        // RulesPreview
        GameScreenState(isRulesDialogOpen = true)
    )

    override fun getDisplayName(index: Int): String? {
        return when (index) {
            0 -> "PlayerPreview"
            1 -> "OpponentPreview"
            2 -> "ResultPreview"
            3 -> "RulesPreview"
            else -> null
        }
    }
}

@Preview
@Composable
private fun RockPaperScissorsContentPreview(
    @PreviewParameter(GameScreenStatePreviewProvider::class) uiState: GameScreenState
) {
    MyApplicationTheme {
        RockPaperScissorsContent(uiState = uiState)
    }
}