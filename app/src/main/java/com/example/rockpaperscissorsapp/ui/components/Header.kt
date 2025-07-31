package com.example.rockpaperscissorsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rockpaperscissorsapp.ui.theme.BorderColor
import com.example.rockpaperscissorsapp.ui.theme.DarkTextColor
import com.example.rockpaperscissorsapp.ui.theme.MyApplicationTheme
import com.example.rockpaperscissorsapp.ui.theme.ScoreBoxColor
import com.example.rockpaperscissorsapp.ui.theme.ScoreTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(score: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(TopAppBarDefaults.windowInsets)
            .border(2.dp, BorderColor, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "ROCK\nPAPER\nSCISSORS",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )

        Column(
            modifier = Modifier
                .width(100.dp)
                .height(90.dp)
                .background(ScoreBoxColor, RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "SCORE",
                color = ScoreTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = score.toString(),
                color = DarkTextColor,
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    MyApplicationTheme {
        Header(1)
    }
    
}