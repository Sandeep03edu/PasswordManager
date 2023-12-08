package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun CountryCodePicker(
    onValueChange : (code: String, phone: String, isValid: Boolean) ->Unit
)