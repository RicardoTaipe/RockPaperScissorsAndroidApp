package com.example.rockpaperscissorsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rockpaperscissorsapp.R

val barlowFamily = FontFamily(
    Font(R.font.barlow_semi_condensed_semibold, FontWeight.SemiBold),
    Font(R.font.barlow_semi_condensed_semibold, FontWeight.Light),
    Font(R.font.barlow_semi_condensed_semibold, FontWeight.Normal),
    Font(R.font.barlow_semi_condensed_semibold, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.barlow_semi_condensed_semibold, FontWeight.Medium),
    Font(R.font.barlow_semi_condensed_semibold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = barlowFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 64.sp, // "YOU WIN"
        lineHeight = 64.sp
    ),
    displayMedium = TextStyle(
        fontFamily = barlowFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 48.sp, // Score number
        lineHeight = 48.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = barlowFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp, // "ROCK PAPER SCISSORS"
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = barlowFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp, // "YOU PICKED", "THE HOUSE PICKED", "RULES", "PLAY AGAIN"
        lineHeight = 24.sp // Default line height for titleMedium
    ),
    labelLarge = TextStyle(
        fontFamily = barlowFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp, // "SCORE"
        lineHeight = 20.sp // Default line height for labelLarge
    )
)