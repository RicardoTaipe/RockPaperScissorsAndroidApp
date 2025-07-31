package com.example.rockpaperscissorsapp.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.Result
import com.example.rockpaperscissorsapp.ui.components.ChoiceButton
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonOuterBlue
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonOuterOrange
import com.example.rockpaperscissorsapp.ui.theme.MyApplicationTheme
import com.example.rockpaperscissorsapp.ui.theme.PlayAgainButtonColor
import com.example.rockpaperscissorsapp.ui.theme.PlayAgainButtonTextColor
import com.example.rockpaperscissorsapp.ui.theme.largeRadialGradient

@Composable
fun GameRoute(modifier: Modifier = Modifier, onPlayAgain: () -> Unit) {
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModel.Factory)
    LaunchedEffect(Unit) {
        gameViewModel.playGame()
    }

    val userChoice by gameViewModel.yourChoice.collectAsStateWithLifecycle()
    val comChoice by gameViewModel.comChoice.collectAsStateWithLifecycle()
    val result by gameViewModel.result.collectAsStateWithLifecycle()

    GameScreen(userChoice, comChoice, result, modifier){
        gameViewModel.resetGame()
        onPlayAgain.invoke()
    }

}

@Composable
private fun GameScreen(
    userChoice: Choice, // Pass the user's choice (Rock, Paper, or Scissors)
    houseChoice: Choice, // Pass the house's choice
    result: Result,
    modifier: Modifier = Modifier,
    onPlayAgain: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Choices Display Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User's Pick
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ChoiceButton(
                    outerColor = ChoiceButtonOuterBlue,
                    iconResId = getIconForChoice(userChoice),
                    contentDescription = userChoice.name
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.you_picked),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // House's Pick
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ChoiceButton(
                    outerColor = ChoiceButtonOuterOrange,
                    iconResId = getIconForChoice(houseChoice),
                    contentDescription = houseChoice.name
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.the_house_picked),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        // Win/Lose/Draw Message
        Text(
            text = stringResource(
                when (result) {
                    Result.WIN -> R.string.you_win
                    Result.DRAW -> R.string.draw
                    Result.LOSE -> R.string.you_lose
                }
            ),
            color = Color.White, fontSize = 64.sp, fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Play Again Button
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
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun getIconForChoice(data: Choice): Int {
    return when (data) {
        Choice.ROCK -> R.drawable.icon_rock
        Choice.PAPER -> R.drawable.icon_paper
        Choice.SCISSORS -> R.drawable.icon_scissors
    }
}

@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
fun PreviewWinScreen() {
    MyApplicationTheme {
        GameScreen(
            userChoice = Choice.ROCK,
            houseChoice = Choice.SCISSORS,
            onPlayAgain = {},
            result = Result.WIN,
            modifier = Modifier.background(brush = largeRadialGradient)
        )
    }
}
