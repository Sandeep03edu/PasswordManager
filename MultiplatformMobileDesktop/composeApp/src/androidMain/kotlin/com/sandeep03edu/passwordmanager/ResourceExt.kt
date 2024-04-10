package com.sandeep03edu.passwordmanager

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.ImageResource

@Composable
actual fun paintResource(imageResource: ImageResource): Painter {
    return androidx.compose.ui.res.painterResource(id = imageResource.drawableResId)
}