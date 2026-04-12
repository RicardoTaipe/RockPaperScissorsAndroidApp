package com.example.rockpaperscissorsapp.play

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissorsapp.data.Move
import com.example.rockpaperscissorsapp.ui.components.MoveButton
import com.example.rockpaperscissorsapp.ui.theme.LineColor
import com.example.rockpaperscissorsapp.ui.theme.largeRadialGradient

@Composable
fun PlayGameScreen(modifier: Modifier = Modifier, onGameChoiceSelected: (Move) -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GameMoves(onGameChoiceSelected)
    }
}

@Composable
fun GameMoves(onMovePlayed: (Move) -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.1f) // Slightly taller than wide for the triangle shape
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 15.dp.toPx()

            // Define relative center points
            val paperPoint = Offset(width * 0.2f, height * 0.25f)
            val scissorsPoint = Offset(width * 0.8f, height * 0.25f)
            val rockPoint = Offset(width * 0.5f, height * 0.8f)

            // Draw the triangle path
            drawPath(
                path = Path().apply {
                    moveTo(paperPoint.x, paperPoint.y)
                    lineTo(scissorsPoint.x, scissorsPoint.y)
                    lineTo(rockPoint.x, rockPoint.y)
                    close()
                },
                color = LineColor,
                style = Stroke(width = strokeWidth, join = StrokeJoin.Round)
            )
        }

        val movePositions = listOf(
            Move.PAPER to Alignment.TopStart,
            Move.SCISSORS to Alignment.TopEnd,
            Move.ROCK to Alignment.BottomCenter
        )

        movePositions.forEach { (move, alignment) ->
            MoveButton(
                move = move,
                modifier = Modifier.align(alignment),
                isAnimationEnabled = true,
                contentDescription = move.name,
                onClick = { onMovePlayed(move) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayScreenPreview() {
    PlayGameScreen(
        modifier = Modifier.background(brush = largeRadialGradient)
    )
}
