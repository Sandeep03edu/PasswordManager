package com.sandeep03edu.passwordmanager.manager.credentials.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeep03edu.passwordmanager.manager.utils.data.generateRandomDarkColor
import com.sandeep03edu.passwordmanager.manager.utils.data.getPasswordTagColor

@Composable
fun PasswordTagCard(
    tagPair: Pair<String, ImageVector>,
    selectedPasswordTag: String?,
    onClick: () -> Unit,
) {
    val tag = tagPair.first
    var containerColor = getPasswordTagColor(tag)
    val textColor = MaterialTheme.colorScheme.surface
    if (selectedPasswordTag != null && selectedPasswordTag == tag) {
        containerColor = MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(containerColor)
            .clickable {
                onClick()
            }
            .padding(horizontal = 20.dp, vertical = 25.dp)
    ) {
        Text(
            text = tag,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .align(Alignment.Center),
            style = TextStyle(
                color = textColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
            )
        )
    }
}