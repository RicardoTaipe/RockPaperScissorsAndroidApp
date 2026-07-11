package com.example.rockpaperscissorsapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.ui.theme.BorderColor
import com.example.rockpaperscissorsapp.ui.theme.MyApplicationTheme
import com.example.rockpaperscissorsapp.ui.theme.RulesButtonTextColor

@Composable
fun RulesButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(160.dp)
            .height(50.dp)
            .border(2.dp, BorderColor, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = stringResource(R.string.rules),
            color = RulesButtonTextColor,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun RulesButtonPreview() {
    MyApplicationTheme {
        RulesButton { }
    }
}