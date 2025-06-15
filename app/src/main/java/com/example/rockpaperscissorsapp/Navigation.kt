package com.example.rockpaperscissorsapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.rockpaperscissorsapp.game.GameScreen
import com.example.rockpaperscissorsapp.play.PlayScreen
import com.example.rockpaperscissorsapp.rules.RulesScreen
import kotlinx.serialization.Serializable

@Serializable
object Play

@Serializable
object Rules

@Serializable
object Game

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Play) {
        dialog<Rules> {
            RulesScreen(onNavigateToPlay = { navController.navigate(Play) })
        }
        composable<Play> {
            PlayScreen(onNavigateToGame = { navController.navigate(Game) })
        }
        composable<Game> {
            GameScreen(onNavigateToRules = { navController.navigate(Rules) })
        }
    }
}