package com.example.rockpaperscissorsapp.play

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.ui.components.ChoiceButton
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonOuterBlue
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonOuterOrange
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonOuterRed
import com.example.rockpaperscissorsapp.ui.theme.LineColor
import com.example.rockpaperscissorsapp.ui.theme.largeRadialGradient

@Composable
fun PlayGameRoute(modifier: Modifier = Modifier, onNavigateToGame: () -> Unit) {
    val playViewModel: PlayViewModel = viewModel(factory = PlayViewModel.Factory)
    PlayGameScreen(modifier = modifier) {
        playViewModel.selectOption(it)
        onNavigateToGame.invoke()
    }
}

@Composable
fun PlayGameScreen(modifier: Modifier = Modifier, onGameChoiceSelected: (Choice) -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GameChoices(onGameChoiceSelected)
    }
}

@Composable
fun GameChoices(onGameChoiceSelected: (Choice) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Maintain square aspect ratio for the game area
    ) {
        // Define positions for each choice button relative to the Box
        // These offsets are approximate and may need fine-tuning for pixel-perfect match
        val paperOffset = Modifier
            .align(Alignment.TopStart)
            .offset(x = (-20).dp, y = 10.dp)
        val scissorsOffset = Modifier
            .align(Alignment.TopEnd)
            .offset(x = 20.dp, y = 10.dp)
        val rockOffset = Modifier
            .align(Alignment.BottomCenter)
            .offset(y = (-20).dp)

        // Draw connecting lines
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Calculate exact centers of the buttons for drawing lines
            // Assuming button radius is about 60.dp (120.dp diameter)
            val buttonRadiusPx = 60.dp.toPx()

            val paperX = 0f + buttonRadiusPx // Top-left button's center X
            val paperY = 0f + buttonRadiusPx // Top-left button's center Y

            val scissorsX = size.width - buttonRadiusPx // Top-right button's center X
            val scissorsY = 0f + buttonRadiusPx // Top-right button's center Y

            val rockX = centerX // Bottom-center button's center X
            val rockY = size.height - buttonRadiusPx // Bottom-center button's center Y

            val strokeWidth = 8.dp.toPx() // Line thickness

            // Line from Paper to Scissors
            drawLine(
                color = LineColor,
                start = Offset(paperX, paperY),
                end = Offset(scissorsX, scissorsY),
                strokeWidth = strokeWidth
            )

            // Line from Paper to Rock
            drawLine(
                color = LineColor,
                start = Offset(paperX, paperY),
                end = Offset(rockX, rockY),
                strokeWidth = strokeWidth
            )

            // Line from Scissors to Rock
            drawLine(
                color = LineColor,
                start = Offset(scissorsX, scissorsY),
                end = Offset(rockX, rockY),
                strokeWidth = strokeWidth
            )
        }

        ChoiceButton(
            modifier = paperOffset,
            outerColor = ChoiceButtonOuterBlue,
            iconResId = R.drawable.icon_paper,
            contentDescription = Choice.PAPER.name,
            onClick = { onGameChoiceSelected.invoke(Choice.PAPER) }
        )

        ChoiceButton(
            modifier = scissorsOffset,
            outerColor = ChoiceButtonOuterOrange,
            iconResId = R.drawable.icon_scissors,
            contentDescription = Choice.SCISSORS.name,
            onClick = { onGameChoiceSelected.invoke(Choice.SCISSORS) }
        )

        ChoiceButton(
            modifier = rockOffset,
            outerColor = ChoiceButtonOuterRed,
            iconResId = R.drawable.icon_rock,
            contentDescription = Choice.ROCK.name,
            onClick = { onGameChoiceSelected.invoke(Choice.ROCK) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayScreenPreview() {
    PlayGameScreen(
        modifier = Modifier.background(brush = largeRadialGradient)
    )
}
