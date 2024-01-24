package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.PinAuthentication
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUser
import com.sandeep03edu.passwordmanager.manager.utils.domain.hashString
import kotlinx.coroutines.CoroutineScope

data class PinAuthenticationDisplayClass(
    var pinLength: Int = 0,
    var label: String,
    val onComplete: (String, SnackbarHostState, CoroutineScope) -> Unit,
    ) : Screen {

    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold (
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ){
            PinAuthenticationDisplay(
                pinLength, label, onComplete = {
                    println("$TAG Got Pin:: $it")
                    onComplete(it, snackbarHostState, coroutineScope)
                }
            )
        }
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

