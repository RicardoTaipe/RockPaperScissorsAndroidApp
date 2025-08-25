package com.example.rockpaperscissorsapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.rockpaperscissorsapp.ui.theme.DarkTextColor
import com.example.rockpaperscissorsapp.ui.theme.RulesButtonTextColor

private val OUTER_CIRCLE_SIZE = 130.dp
private val INNER_CIRCLE_SIZE = 100.dp

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
            .size(OUTER_CIRCLE_SIZE)
            .clip(CircleShape)
            .background(outerColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(INNER_CIRCLE_SIZE)
                .clip(CircleShape)
                .background(ChoiceButtonInnerCircle),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(iconResId), contentDescription = contentDescription)
        }
    }
}

@Composable
fun HouseButton(
    modifier: Modifier = Modifier,
    counter: Long
) {
    Box(
        modifier = modifier
            .size(OUTER_CIRCLE_SIZE)
            .clip(CircleShape)
            .background(DarkTextColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = counter.toString(),
            color = RulesButtonTextColor,
            style = MaterialTheme.typography.displayMedium
        )
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

@Preview
@Composable
private fun HouseButtonPreview() {
    HouseButton(counter = 3)
}