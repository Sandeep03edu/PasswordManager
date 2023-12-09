package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.PinAuthentication
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState

data class PinAuthenticationDisplayClass(
    var usr: UserState,
    var label: String,
) : Screen {

    @Composable
    override fun Content() {
        PinAuthenticationDisplay(
            usr, label
        )
    }

}

@Composable
fun PinAuthenticationDisplay(
    usr: UserState,
    label: String,
) {
    var pinLength = 0;
    if (label == "Login pin") {
        pinLength = 4
    } else if (label == "App Pin") {
        pinLength = 6
    }

    PinAuthentication(
        label = label,
        pinLength = pinLength
    ) { inputPin ->
        println("$TAG Auth Pin: $inputPin")

        // TODO: Convert User's pin into hash before comparing
        val userLoginPin = usr.loginPin

        if (userLoginPin == inputPin) {
            // TODO: Move to Data page
            println("${com.sandeep03edu.passwordmanager.TAG} Login Pin matched")
        } else {
            println("${com.sandeep03edu.passwordmanager.TAG} Login Pin mis-matched - $userLoginPin")
        }

    }

}

