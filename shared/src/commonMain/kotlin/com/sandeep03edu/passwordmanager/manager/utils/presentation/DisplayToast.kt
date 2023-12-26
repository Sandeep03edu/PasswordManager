package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun DisplaySnackbarToast(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    message: String
){
    scope.launch {
        snackbarHostState.showSnackbar(message = message)
    }
}

fun Modifier.bottomDialogModifier() = layout { measurable, constraints ->

    val placeable = measurable.measure(constraints);
    layout(constraints.maxWidth, constraints.maxHeight){
        placeable.place(0, constraints.maxHeight - placeable.height, 10f)
    }
}