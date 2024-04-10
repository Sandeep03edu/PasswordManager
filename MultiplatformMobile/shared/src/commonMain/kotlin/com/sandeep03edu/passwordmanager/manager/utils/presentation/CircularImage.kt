package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.sandeep03edu.passwordmanager.SharedRes
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun CircularImage(
    painter: Painter,
    modifier: Modifier = Modifier.size(150.dp),
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            )
        ) {
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
                    .clickable(
                        enabled = onClick != null,
                        onClick = {
                            if (onClick != null) {
                                onClick()
                            }
                        }),
                contentDescription = "Circular Image"
            )
        }
    }

}