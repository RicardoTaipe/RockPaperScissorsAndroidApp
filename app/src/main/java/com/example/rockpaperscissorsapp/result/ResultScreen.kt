package com.example.rockpaperscissorsapp.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.data.GameResult
import com.example.rockpaperscissorsapp.data.Move
import com.example.rockpaperscissorsapp.game.GameUiState
import com.example.rockpaperscissorsapp.game.MoveContainer
import com.example.rockpaperscissorsapp.ui.components.MoveButton
import com.example.rockpaperscissorsapp.ui.theme.MyApplicationTheme
import com.example.rockpaperscissorsapp.ui.theme.PlayAgainButtonColor
import com.example.rockpaperscissorsapp.ui.theme.PlayAgainButtonTextColor
import com.example.rockpaperscissorsapp.ui.theme.largeRadialGradient


@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    uiState: GameUiState.Result,
    onPlayAgain: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(64.dp, Alignment.CenterVertically),
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User's Pick
            MoveContainer(text = stringResource(R.string.you_picked)) {
                uiState.playerMove.let {
                    MoveButton(
                        move = it,
                        contentDescription = it.name,
                    )
                }
            }

            // House's Pick
            MoveContainer(text = stringResource(R.string.the_house_picked)) {
                uiState.opponentMove.let {
                    MoveButton(
                        move = it,
                        contentDescription = it.name,
                    )
                }
            }
        }
        ResultContainer(uiState = uiState, onPlayAgain = onPlayAgain)
    }
}

@Composable
private fun ResultContainer(
    modifier: Modifier = Modifier,
    uiState: GameUiState.Result,
    onPlayAgain: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = stringResource(
                when (uiState.gameResult) {
                    GameResult.WIN -> R.string.you_win
                    GameResult.DRAW -> R.string.draw
                    GameResult.LOSE -> R.string.you_lose
                }
            ),
            color = Color.White,
            style = MaterialTheme.typography.displayLarge
        )

        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .width(220.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = PlayAgainButtonColor),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = stringResource(R.string.play_again),
                color = PlayAgainButtonTextColor,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWinScreen() {
    MyApplicationTheme {
        ResultScreen(
            uiState = GameUiState.Result(
                playerMove = Move.ROCK,
                opponentMove = Move.SCISSORS,
                score = 1,
                gameResult = GameResult.WIN,
            ),
            modifier = Modifier.background(brush = largeRadialGradient),
            onPlayAgain = {},
        )
    }
}
