package com.sandeep03edu.passwordmanager.manager.credentials.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sandeep03edu.passwordmanager.manager.utils.data.generateRandomLightColor
import kotlinx.datetime.Clock
import kotlin.random.Random




@Composable
fun TagCard(
    text: String,
    isSelected: Boolean,
    onClick : () -> Unit
) {

    val backgroundColor = if(isSelected) Color.DarkGray else generateRandomLightColor()
    val textColor = if(isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(text = text,
            color = textColor)
    }
}