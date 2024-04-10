package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space
import dev.icerock.moko.resources.ImageResource
import org.jetbrains.compose.resources.painterResource

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
                    .clipToBounds()
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
                        Text(text = prefix)
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
                    trailingIcon = {
                        if (errorMessage != null) {
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )


            }

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.error,
                        background = MaterialTheme.colorScheme.onError
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
                    .clipToBounds()
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
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.onPrimary,

                        // Removing Indicator
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,


                        disabledContainerColor = MaterialTheme.colorScheme.background,
                    ),

                    label = { Text(label) },
                    leadingIcon = {
                        if (imageVector != null) {
                            Icon(
                                imageVector,
                                label,
                                tint = if (borderColor == Color.LightGray) Color.DarkGray else MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    trailingIcon = {
                        if (errorMessage != null) {
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = MaterialTheme.colorScheme.error,
                            )
                        } else {
                            Icon(icon, "contentDescription")
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
                ) {
                    dropDownItems.forEachIndexed { idx, item ->
                        DropdownMenuItem(
                            onClick = {
                                onSelectedItem(item)
                                isDropDownOpen = false
                            },
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (dropDownItemIcons != null) {
                                        Image(
                                            painter = paintResource(dropDownItemIcons[idx]),
                                            "",
                                            modifier = Modifier.size(40.dp)
                                        )
                                        space(width = 12)
                                    }
                                    Text(text = item)
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
                        color = MaterialTheme.colorScheme.error,
                        background = MaterialTheme.colorScheme.onError
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
                    .clipToBounds()
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
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.onPrimary,

                        // Removing Indicator
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
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
                    trailingIcon = {
                        if (errorMessage != null) {
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
            }

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.error,
                        background = MaterialTheme.colorScheme.onError
                    ),
                    modifier = Modifier.padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 4.dp)
                )
            }
        }
    }
}
