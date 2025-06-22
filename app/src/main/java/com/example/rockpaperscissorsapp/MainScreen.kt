package com.example.rockpaperscissorsapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rockpaperscissorsapp.game.GameScreen
import com.example.rockpaperscissorsapp.play.PlayScreen
import com.example.rockpaperscissorsapp.rules.RulesScreen

@Composable
fun MainScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF1F3756), Color(0xFF141539))
                )
            )
            .padding(24.dp)
            .safeDrawingPadding()
    ) {
        val navController = rememberNavController()
        var openAlertDialog by remember { mutableStateOf(false) }

        ScoreCard()

        NavHost(
            navController = navController,
            startDestination = Play,
            modifier = Modifier.weight(1f)
        ) {
            composable<Play> {
                PlayScreen(onNavigateToGame = { navController.navigate(Game) })
            }
            composable<Game> {
                GameScreen(onNavigateToRules = { navController.navigate(Play) })
            }
        }
        if (openAlertDialog) {
            RulesScreen { openAlertDialog = false }
        }
        RulesButton { openAlertDialog = true }
    }
}

@Composable
private fun ScoreCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Color(0xFF606E85))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.logo),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.score),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "0",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
private fun RulesButton(onNavigateRules: ()-> Unit) {
    OutlinedButton(
        onClick = onNavigateRules,
        border = BorderStroke(2.dp, Color(0xFF606E85)),
        modifier = Modifier
    ) {
        Text(
            text = stringResource(R.string.rules),
            color = Color.White
        )
    }
}


@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}