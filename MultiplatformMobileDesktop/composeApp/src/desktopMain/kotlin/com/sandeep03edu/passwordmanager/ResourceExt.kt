package com.sandeep03edu.passwordmanager

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toPainter
import androidx.compose.ui.res.loadImageBitmap
import dev.icerock.moko.resources.ImageResource
import java.io.File

@Composable
actual fun paintResource(imageResource: ImageResource): Painter {
    return imageResource.image.toPainter()
}