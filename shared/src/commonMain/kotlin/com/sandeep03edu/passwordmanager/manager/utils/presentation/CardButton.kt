package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardButton(
    backgroundColor: Color,
    text: String,
    clickEnabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
            .padding(horizontal = 10.dp)
            .clickable(
                enabled = clickEnabled,
                onClick = {
                    onClick()
                }
            ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp, color = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier.fillMaxWidth()
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}