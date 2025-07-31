package com.example.rockpaperscissorsapp.ui.theme

import android.graphics.Shader
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Define custom colors (reusing from previous screen for consistency)
val DarkBlueBackground = Color(0xFF1A2949)
val BorderColor = Color(0xff606e85)
val ScoreBoxColor = Color(0xFFFFFFFF)
val ScoreTextColor = Color(0xff2a46c0)
val DarkTextColor = Color(0xFF3b4363)
val ChoiceButtonOuterBlue = Color(0xFF4865F4)
val ChoiceButtonOuterOrange = Color(0xFFDA8B18)
val ChoiceButtonOuterRed = Color(0xFFDD405D)
val ChoiceButtonInnerCircle = Color(0xFFE8E8E8)
val RulesButtonTextColor = Color(0xFFFFFFFF)
val PlayAgainButtonColor = Color(0xFFFFFFFF)
val PlayAgainButtonTextColor = Color(0xFF1A2949) // Dark blue from background
val LineColor = Color(0xFF3b4363)

val largeRadialGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return RadialGradientShader(
            colors = listOf(Color(0xff1f3756), Color(0xff141539)),
            center = size.center,
            radius = biggerDimension * 2,
        )
    }
}