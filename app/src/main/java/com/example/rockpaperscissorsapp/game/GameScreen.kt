package com.example.rockpaperscissorsapp.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.data.Move
import com.example.rockpaperscissorsapp.ui.components.HouseButton
import com.example.rockpaperscissorsapp.ui.components.MoveButton
import com.example.rockpaperscissorsapp.ui.theme.MyApplicationTheme
import com.example.rockpaperscissorsapp.ui.theme.largeRadialGradient

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    uiState: GameUiState.WaitingForOpponent,
) {
    Column(
        verticalArrangement = Arrangement.Center,
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
                HouseButton(counter = uiState.countdown ?: 0)
            }
        }
    }
}

@Composable
fun MoveContainer(
    modifier: Modifier = Modifier,
    text: String,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        content()
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWinScreen() {
    MyApplicationTheme {
        GameScreen(
            uiState = GameUiState.WaitingForOpponent(
                playerMove = Move.ROCK,
                countdown = 3,
                score = 1
            ),
            modifier = Modifier.background(brush = largeRadialGradient),
        )
    }
}
