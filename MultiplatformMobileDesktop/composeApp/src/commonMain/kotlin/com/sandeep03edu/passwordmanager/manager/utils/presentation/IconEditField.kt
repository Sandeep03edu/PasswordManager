package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space
import com.sandeep03edu.passwordmanager.ui.theme.getErrorTint
import com.sandeep03edu.passwordmanager.ui.theme.getErrorTextColor
import com.sandeep03edu.passwordmanager.ui.theme.getTextColorInverse
import com.sandeep03edu.passwordmanager.ui.theme.getTextEditFieldBackground
import dev.icerock.moko.resources.ImageResource

@Composable
fun IconEditTextField(
    leadingIcon: ImageVector?,
    label: String,
    text: String,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    prefix: String = "",
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
) {
    var borderColor by remember { mutableStateOf(Color.LightGray) }

    Box(
        modifier = modifier
            .clipToBounds()
    ) {

        Column() {
            Box(
                modifier = modifier
                    .padding(4.dp)
                    .border(2.dp, borderColor, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            if (onClick != null) {
                                onClick()
                            }
                        }
                        .onFocusChanged {
                            val isFoc = it.isFocused
                            println("Focus Changed to $isFoc")
                            if (isFoc) {
                                borderColor = Color.DarkGray
                            } else {
                                borderColor = Color.LightGray
                            }
                        },
                    prefix = {
                        Text(
                            text = prefix,
                            color = getTextColorInverse(),
                            modifier = Modifier
                                .background(Color.Transparent)
                        )
                    },
                    enabled = enabled,
                    value = text,
                    onValueChange = {
                        onTextChange(it)
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = getTextEditFieldBackground(),
                        focusedContainerColor = getTextEditFieldBackground(),

                        focusedTextColor = getTextColorInverse(),
                        unfocusedTextColor = getTextColorInverse(),

                        focusedLabelColor = getTextColorInverse(),
                        unfocusedLabelColor = getTextColorInverse(),

                        // Removing Indicator
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,

                        disabledContainerColor = getTextEditFieldBackground(),
                        disabledTextColor = getTextColorInverse(),
                        disabledLabelColor = getTextColorInverse(),
                    ),

                    label = {
                        Text(
                            label,
                            color = getTextColorInverse(),
                            modifier = Modifier
                                .background(Color.Transparent)
                        )
                    },
                    leadingIcon = {
                        if (leadingIcon != null) {
                            Icon(
                                leadingIcon,
                                label,
                                tint = getTextColorInverse()
                            )
                        }
                    },
                    trailingIcon = {
                        if (errorMessage != null) {
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = getErrorTint()
                            )
                        }
                    }
                )


            }

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        color = getErrorTextColor(),
                        background = Color.Transparent
                    ),
                    modifier = Modifier.padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 4.dp)
                )
            }


        }
    }

}

@Composable
fun IconDropDownField(
    imageVector: ImageVector?,
    label: String,
    text: String,
    errorMessage: String? = null,
    dropDownItems: List<String> = emptyList(),
    dropDownItemIcons: List<ImageResource>? = null,
    modifier: Modifier = Modifier,
    onSelectedItem: (String) -> Unit,
) {
    var borderColor by remember { mutableStateOf(Color.LightGray) }
    var isDropDownOpen by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (isDropDownOpen)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Box(
        modifier = modifier
            .clipToBounds()
    ) {

        Column() {
            Box(
                modifier = modifier
                    .padding(4.dp)
                    .border(2.dp, borderColor, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth()
                        .onGloballyPositioned { textfieldSize = it.size.toSize() }
                        .onFocusChanged {
                            val isFoc = it.isFocused
                            if (isFoc) {
                                borderColor = Color.DarkGray
                            } else {
                                borderColor = Color.LightGray
                            }

                        }
                        .clickable {
                            isDropDownOpen = !isDropDownOpen
                        },
                    value = text,
                    onValueChange = {},
                    enabled = false,
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = getTextEditFieldBackground(),
                        focusedContainerColor = getTextEditFieldBackground(),

                        focusedTextColor = getTextColorInverse(),
                        unfocusedTextColor = getTextColorInverse(),

                        focusedLabelColor = getTextColorInverse(),
                        unfocusedLabelColor = getTextColorInverse(),

                        // Removing Indicator
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,

                        disabledContainerColor = getTextEditFieldBackground(),
                        disabledTextColor = getTextColorInverse(),
                        disabledLabelColor = getTextColorInverse(),
                    ),

                    label = {
                        Text(
                            label,
                            color = getTextColorInverse(),
                            modifier = Modifier
                                .background(Color.Transparent)
                        )
                    },
                    leadingIcon = {
                        if (imageVector != null) {
                            Icon(
                                imageVector,
                                label,
                                tint = getTextColorInverse()
                            )
                        }
                    },
                    trailingIcon = {
                        if (errorMessage != null) {
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = getErrorTint(),
                            )
                        } else {
                            Icon(
                                icon,
                                "contentDescription",
                                tint = getTextColorInverse()
                            )
                        }
                    }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                DropdownMenu(
                    expanded = isDropDownOpen,
                    onDismissRequest = {
                        isDropDownOpen = false
                    },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
//                        .fillMaxWidth(0.9f)
                        .requiredHeightIn(min = textfieldSize.height.dp, max = 250.dp)
                        .background(getTextEditFieldBackground())
                ) {
                    dropDownItems.forEachIndexed { idx, item ->
                        DropdownMenuItem(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(getTextEditFieldBackground()),
                            onClick = {
                                onSelectedItem(item)
                                isDropDownOpen = false
                            },
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                        .background(getTextEditFieldBackground())
                                ) {
                                    if (dropDownItemIcons != null) {
                                        Image(
                                            painter = paintResource(dropDownItemIcons[idx]),
                                            "",
                                            modifier = Modifier.size(40.dp)
                                        )
                                        space(width = 12)
                                    }
                                    Text(
                                        text = item,
                                        color = getTextColorInverse()
                                    )
                                }
                            },
                        )
                    }
                }

            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        color = getErrorTextColor(),
                        background = Color.Transparent
                    ),
                    modifier = Modifier.padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 4.dp)
                )
            }


        }
    }

}

@Composable
fun IconEditNumberField(
    leadingIcon: ImageVector?,
    label: String,
    text: String,
    errorMessage: String? = null,
    maxLength: Int = 1000,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
) {
    var borderColor by remember { mutableStateOf(Color.LightGray) }

    Box(
        modifier = modifier
            .clipToBounds()
    ) {
        Column() {
            Box(
                modifier = modifier
                    .padding(4.dp)
                    .border(2.dp, borderColor, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth()
                        .onFocusChanged {
                            val isFoc = it.isFocused
                            println("Focus Changed to $isFoc")
                            if (isFoc) {
                                borderColor = Color.DarkGray
                            } else {
                                borderColor = Color.LightGray
                            }

                        },
                    value = text,
                    onValueChange = {
                        if (it.length <= maxLength) {
                            onTextChange(it)
                        }
                    },
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = getTextEditFieldBackground(),
                        focusedContainerColor = getTextEditFieldBackground(),

                        focusedTextColor = getTextColorInverse(),
                        unfocusedTextColor = getTextColorInverse(),

                        focusedLabelColor = getTextColorInverse(),
                        unfocusedLabelColor = getTextColorInverse(),

                        // Removing Indicator
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,

                        disabledContainerColor = getTextEditFieldBackground(),
                        disabledTextColor = getTextColorInverse(),
                        disabledLabelColor = getTextColorInverse(),
                    ),
                    label = {
                        Text(
                            label,
                            color = getTextColorInverse(),
                            modifier = Modifier
                                .background(Color.Transparent)
                        )
                    },
                    leadingIcon = {
                        if (leadingIcon != null) {
                            Icon(
                                leadingIcon,
                                label,
                                tint = getTextColorInverse()
                            )
                        }
                    },
                    trailingIcon = {
                        if (errorMessage != null) {
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = getErrorTint()
                            )
                        }
                    }
                )
            }

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        color = getErrorTextColor(),
                        background = Color.Transparent
                    ),
                    modifier = Modifier.padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 4.dp)
                )
            }
        }
    }
}
