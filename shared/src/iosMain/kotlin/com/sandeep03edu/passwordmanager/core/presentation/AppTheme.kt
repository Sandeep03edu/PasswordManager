package com.sandeep03edu.passwordmanager.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.compose.DarkColors
import com.example.compose.LightColors
import com.sandeep03edu.passwordmanager.ui.theme.Typography

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    // IoS doesn't support dynamic color so it just uses MaterialTheme
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}