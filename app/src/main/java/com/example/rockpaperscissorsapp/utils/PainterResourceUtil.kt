package com.example.rockpaperscissorsapp.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter

object PainterResourceUtil {

    /**
     * Resolves the appropriate Painter for images based on the current environment.
     *
     * @param id Resource ID of the drawable used in preview mode.
     * @param networkImageUrl URL of the image to load at runtime. If null, uses the drawable resource.
     * @return A Painter that can be used to display the image.
     */
    @Composable
    fun resolvePainter(@DrawableRes id: Int, networkImageUrl: String? = null): Painter {
        val isPreview = LocalInspectionMode.current
        return if (isPreview) {
            // Use painterResource in preview mode
            painterResource(id = id)
        } else {
            // Use rememberAsyncImagePainter for runtime image loading
            rememberAsyncImagePainter(model = networkImageUrl ?: id)
        }
    }
}