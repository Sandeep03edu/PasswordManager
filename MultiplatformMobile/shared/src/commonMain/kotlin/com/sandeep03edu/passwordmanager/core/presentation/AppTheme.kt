package com.sandeep03edu.passwordmanager.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)