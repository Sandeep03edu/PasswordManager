package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconLabeledTextField(
    leadingIcon: ImageVector?,
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    prefix: String = "",
    onClick: (() -> Unit)? = null,
) {
    var borderColor by remember { mutableStateOf(Color.LightGray) }

    Box(
        modifier = modifier
            .clipToBounds()
            .clickable(enabled = onClick != null) {
                if (onClick != null) {
                    onClick()
                }
            }
    ) {

        Column() {
            Box(
                modifier = modifier
                    .padding(4.dp)
                    .border(2.dp, borderColor, RoundedCornerShape(8.dp))
                    .clipToBounds()
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {},
                    enabled = false,
                    value = text,
                    prefix = {
                        Text(text = prefix)
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.onPrimary,

                        // Removing Indicator
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,

                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                    ),

                    label = { Text(label) },
                    leadingIcon = {
                        if (leadingIcon != null) {
                            Icon(
                                leadingIcon,
                                label,
                                tint = if (borderColor == Color.LightGray) Color.DarkGray else MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                )
            }
        }
    }

}
