package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.togitech.ccp.component.TogiCountryCodePicker

@Composable
actual fun CountryCodePicker(
    onValueChange: (code: String, phone: String, isValid: Boolean) -> Unit
) {
    TogiCountryCodePicker(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(MaterialTheme.colorScheme.background),
        onValueChange = { (code, phone), isValid ->
            onValueChange(code, phone, isValid)
        },
        label = { Text("Phone Number") },
        initialCountryPhoneCode = "+91",
        showError = true
    )
}