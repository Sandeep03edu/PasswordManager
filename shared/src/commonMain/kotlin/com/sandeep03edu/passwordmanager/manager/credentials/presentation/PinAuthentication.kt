package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.PinAuthentication
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUser
import com.sandeep03edu.passwordmanager.manager.utils.domain.hashString

data class PinAuthenticationDisplayClass(
    var pinLength: Int = 0,
    var label: String,
    val onComplete: (String) -> Unit,

    ) : Screen {

    @Composable
    override fun Content() {
        PinAuthenticationDisplay(
            pinLength, label, onComplete
        )
    }

}

@Composable
fun PinAuthenticationDisplay(
    pinLength: Int = 0,
    label: String,
    onComplete: (String) -> Unit,
) {

    PinAuthentication(
        label = label,
        pinLength = pinLength
    ) { inputPin ->
        println("$TAG Auth Pin: $inputPin")

        if (inputPin.length == pinLength) {
            onComplete(inputPin)
        }
    }
}

