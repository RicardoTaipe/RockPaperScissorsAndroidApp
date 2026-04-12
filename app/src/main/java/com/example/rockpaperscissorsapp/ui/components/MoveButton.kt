package com.example.rockpaperscissorsapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.data.Move
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonInnerCircle
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonOuterBlue
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonOuterOrange
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonOuterRed
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonShadowBlue
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonShadowOrange
import com.example.rockpaperscissorsapp.ui.theme.ChoiceButtonShadowRed
import com.example.rockpaperscissorsapp.ui.theme.DarkTextColor
import com.example.rockpaperscissorsapp.ui.theme.RulesButtonTextColor

private val OUTER_CIRCLE_SIZE = 100.dp

@Composable
fun MoveButton(
    modifier: Modifier = Modifier,
    move: Move,
    contentDescription: String,
    isAnimationEnabled: Boolean = false,
    onClick: () -> Unit = {},
) {
    val style = move.toStyle()

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val targetScale = if (isAnimationEnabled && isPressed) 1.2f else 1.0f

    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = if (isPressed) {
            // Fast out slow in for the press
            tween(durationMillis = 150, easing = FastOutSlowInEasing)
        } else {
            // Overshoot effect for the release
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        },
        label = "buttonScale"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .padding(8.dp)
            .size(OUTER_CIRCLE_SIZE)
            .dropShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 0.dp,
                    spread = 0.dp,
                    color = style.shadowColor,
                    offset = DpOffset(x = 0.dp, 4.dp)
                )
            )
            .border(8.dp, style.outerColor, CircleShape)
            .background(
                color = ChoiceButtonInnerCircle,
                shape = CircleShape
            )
            .innerShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 0.dp,
                    spread = 8.dp,
                    color = Color.Gray,
                    offset = DpOffset(x = 0.dp, 4.dp)
                )
            )
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(style.iconResId),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun HouseButton(
    modifier: Modifier = Modifier,
    counter: Int
) {
    Box(
        modifier = modifier
            .padding(8.dp)
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

/**
 * Extension function to map domain Move to UI MoveStyle.
 * This keeps the UI logic separate from the domain model.
 */
private fun Move.toStyle(): MoveStyle = when (this) {
    Move.ROCK -> MoveStyle.Rock
    Move.PAPER -> MoveStyle.Paper
    Move.SCISSORS -> MoveStyle.Scissors
}

private sealed class MoveStyle(
    @field:DrawableRes val iconResId: Int,
    val outerColor: Color,
    val shadowColor: Color,
) {
    data object Rock : MoveStyle(
        iconResId = R.drawable.icon_rock,
        outerColor = ChoiceButtonOuterRed,
        shadowColor = ChoiceButtonShadowRed,
    )

    data object Paper : MoveStyle(
        iconResId = R.drawable.icon_paper,
        outerColor = ChoiceButtonOuterBlue,
        shadowColor = ChoiceButtonShadowBlue,
    )

    data object Scissors : MoveStyle(
        iconResId = R.drawable.icon_scissors,
        outerColor = ChoiceButtonOuterOrange,
        shadowColor = ChoiceButtonShadowOrange,
    )
}


class MovePreviewParameterProvider : PreviewParameterProvider<Move> {
    override val values = Move.entries.asSequence()
    override fun getDisplayName(index: Int): String = Move.entries[index].name
}


@Preview
@Composable
private fun MoveButtonPreview(
    @PreviewParameter(MovePreviewParameterProvider::class) move: Move
) {
    MoveButton(
        move = move,
        contentDescription = move.name,
        isAnimationEnabled = true
    )
}

@Preview
@Composable
private fun HouseButtonPreview() {
    HouseButton(counter = 3)
}