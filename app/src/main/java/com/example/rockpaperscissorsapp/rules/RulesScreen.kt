package com.example.rockpaperscissorsapp.rules

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.rockpaperscissorsapp.R
import com.example.rockpaperscissorsapp.ui.theme.DarkTextColor
import com.example.rockpaperscissorsapp.ui.theme.MyApplicationTheme
import com.example.rockpaperscissorsapp.utils.PainterResourceUtil.resolvePainter

@Composable
fun RulesScreen(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(32.dp)
                .safeDrawingPadding()
        ) {

            Text(
                text = stringResource(id = R.string.rules),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier
                    .align(Alignment.TopCenter),
                color = DarkTextColor
            )


            Image(
                painter = resolvePainter(id = R.drawable.image_rules),
                contentDescription = stringResource(id = R.string.rules_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )


            Image(
                painter = resolvePainter(id = R.drawable.icon_close),
                contentDescription = stringResource(id = R.string.close_button),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clickable(onClick = onDismissRequest)
            )
        }
    }
}


@Preview
@Composable
private fun RulesScreenPreview() {
    MyApplicationTheme {
        RulesScreen {}
    }
}