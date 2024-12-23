package com.example.rockpaperscissorsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissorsapp.utils.PainterResourceUtil.resolvePainter

@Composable
fun RulesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Rules Text
        Text(
            text = stringResource(id = R.string.rules),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )

        // Rules Image
        Image(
            painter = resolvePainter(id = R.drawable.image_rules),
            contentDescription = stringResource(id = R.string.rules_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )

        // Close Button
        Image(
            painter = resolvePainter(id = R.drawable.icon_close),
            contentDescription = stringResource(id = R.string.close_button),
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomCenter)
        )
    }
}


@Preview
@Composable
private fun RulesScreenPreview() {
    RulesScreen()
}