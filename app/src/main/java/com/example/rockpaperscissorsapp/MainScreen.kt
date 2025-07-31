package com.example.rockpaperscissorsapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rockpaperscissorsapp.game.GameRoute
import com.example.rockpaperscissorsapp.play.PlayGameRoute
import com.example.rockpaperscissorsapp.rules.RulesScreen
import com.example.rockpaperscissorsapp.ui.components.Header
import com.example.rockpaperscissorsapp.ui.theme.BorderColor
import com.example.rockpaperscissorsapp.ui.theme.MyApplicationTheme
import com.example.rockpaperscissorsapp.ui.theme.RulesButtonTextColor
import com.example.rockpaperscissorsapp.ui.theme.largeRadialGradient

@Composable
fun RockPaperScissorsApp(
    navController: NavHostController = rememberNavController()
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(Modifier.padding(horizontal = 24.dp, vertical = 32.dp)) {
                Header(score = 0)
            }
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                RulesButton { openAlertDialog = true }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(brush = largeRadialGradient)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(64.dp))

                NavHost(
                    navController = navController,
                    startDestination = PlayRoute,
                    modifier = Modifier.weight(1f).padding(horizontal = 24.dp)
                ) {
                    composable<PlayRoute> {
                        PlayGameRoute { navController.navigate(GameRoute) }
                    }
                    composable<GameRoute> {
                        GameRoute {
                            navController.navigate(PlayRoute){
                                popUpTo(PlayRoute) { inclusive = true }
                            }
                        }
                    }
                }

                if (openAlertDialog) {
                    RulesScreen { openAlertDialog = false }
                }
            }
        }
    }
}

@Composable
fun RulesButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(160.dp)
            .height(50.dp)
            .border(2.dp, BorderColor, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "RULES",
            color = RulesButtonTextColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MyApplicationTheme {
        RockPaperScissorsApp()
    }
}