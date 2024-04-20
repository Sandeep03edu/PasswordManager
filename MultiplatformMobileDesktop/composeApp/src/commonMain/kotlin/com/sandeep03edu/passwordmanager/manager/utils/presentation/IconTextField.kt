package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Error
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeep03edu.passwordmanager.space
import com.sandeep03edu.passwordmanager.ui.theme.getTextColor
import com.sandeep03edu.passwordmanager.ui.theme.getTextColorInverse
import com.sandeep03edu.passwordmanager.ui.theme.getTextEditFieldBackground

@Composable
fun IconLabeledTextField(
    leadingIcon: ImageVector?,
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    prefix: String = "",
    onClick: (() -> Unit)? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    trailingIcon: ImageVector? = null,

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
            .background(Color.Transparent)
    ) {

        Column() {
            Box(
                modifier = modifier
                    .padding(4.dp)
                    .border(2.dp, borderColor, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {},
                    enabled = false,
                    textStyle = TextStyle(
                        fontSize = fontSize
                    ),
                    value = text,
                    prefix = {
                        Text(text = prefix,
                            color = getTextColorInverse(),
                            modifier = Modifier
                                .background(Color.Transparent)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        // Removing Indicator
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,

                        disabledContainerColor = getTextEditFieldBackground(),
                        disabledTextColor = getTextColorInverse(),
                        disabledLabelColor = getTextColorInverse(),
                    ),

                    label = { Text(label) },
                    leadingIcon = {
                        if (leadingIcon != null) {
                            Icon(
                                leadingIcon,
                                label,
//                                tint = if (borderColor == Color.LightGray) Color.DarkGray else MaterialTheme.colorScheme.primary
                                tint = getTextColorInverse()
                            )
                        }
                    },
                    trailingIcon = {
                        if (trailingIcon != null) {
                            Icon(
                                trailingIcon,
                                "error",
                                tint = getTextColorInverse()
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun IconText(
    leadingIcon: ImageVector,
    text: String,
    color: Color = getTextColorInverse(),
    fontSize: TextUnit = 15.sp,
    iconSize: Dp = 16.dp,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        Icon(
            imageVector = leadingIcon,
            contentDescription = text,
            tint = color,
            modifier = Modifier.size(iconSize)
        )
        space(0, 2)
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.SemiBold,
                color = color
            ),
        )
    }
}


