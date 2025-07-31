package com.example.rockpaperscissorsapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonInnerCircle
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonOuterRed

@Composable
fun ChoiceButton(
    modifier: Modifier = Modifier,
    outerColor: Color,
    @DrawableRes iconResId: Int,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(130.dp) // Outer circle size
            .clip(CircleShape)
            .background(outerColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp) // Inner circle size
                .clip(CircleShape)
                .background(ChoiceButtonInnerCircle),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(iconResId), contentDescription = contentDescription)
        }
    }
}

@Preview
@Composable
private fun ChoiceButtonPreview() {
    ChoiceButton(
        outerColor = ChoiceButtonOuterRed,
        iconResId = R.drawable.icon_rock,
        contentDescription = Choice.ROCK.name,
    )
}