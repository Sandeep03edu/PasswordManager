package com.sandeep03edu.passwordmanager.manager.utils.domain

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.sandeep03edu.passwordmanager.manager.utils.presentation.DisplaySnackbarToast
import kotlinx.coroutines.CoroutineScope


fun onCopyClick(
    text: String,
    clipboardManager: ClipboardManager,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    clipboardManager.setText(AnnotatedString(text))

    DisplaySnackbarToast(
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope,
        message = "Copied to clipboard!!"
    )
}