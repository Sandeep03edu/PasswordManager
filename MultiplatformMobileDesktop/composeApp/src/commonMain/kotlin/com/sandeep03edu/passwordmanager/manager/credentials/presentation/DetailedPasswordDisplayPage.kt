package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.domain.PasswordSize
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.TagCard
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.utils.domain.onCopyClick
import com.sandeep03edu.passwordmanager.manager.utils.presentation.DisplaySnackbarToast
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconLabeledTextField
import com.sandeep03edu.passwordmanager.space
import com.sandeep03edu.passwordmanager.ui.theme.getFloatingActionButtonColor
import com.sandeep03edu.passwordmanager.ui.theme.getTextColorInverse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first


data class DetailedPasswordDisplayPageClass(
    val appModule: AppModule,
    var password: Password,
) : Screen {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { CredentialViewModel(appModule.credentialDataSource) }
        val state by viewModel.state.collectAsState()
        val onEvent = viewModel::onEvent

        LaunchedEffect(state.isAddNewCredentialSheetOpen) {
            password = appModule.credentialDataSource.getPasswordById(password.appId).first()
        }

        val windowSizeClass = calculateWindowSizeClass()
        var numOfRows = 1
        val passwordSize = PasswordSize()
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                numOfRows = 1
                passwordSize.fontHeaderSize = 20.sp
            }

            WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
                numOfRows = 2
                passwordSize.fontHeaderSize = 24.sp
            }
        }

        DetailedPasswordDisplayPage(viewModel, state, onEvent, password, numOfRows, passwordSize)
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailedPasswordDisplayPage(
    viewModel: CredentialViewModel,
    state: CredentialState,
    onEvent: (event: CredentialEvent) -> Unit,
    password: Password,
    numOfRows: Int,
    passwordSize: PasswordSize,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (!state.isAddNewCredentialSheetOpen) {
                FloatingActionButton(
                    onClick = {
                        onEvent(CredentialEvent.OnDisplayAddEditNewDataClick(password, null))
                    },
                    shape = RoundedCornerShape(20.dp),
                    containerColor = getFloatingActionButtonColor(),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Edit Password",
                        tint = getTextColorInverse()
                    )
                }
            }
        }
    ) {
        val clipboardManager: ClipboardManager = LocalClipboardManager.current

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            columns = GridCells.Fixed(numOfRows)
        ) {
            item(span = { GridItemSpan(numOfRows) }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString
                    {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 40.sp
                            )
                        ) {
                            append("Pass")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        ) {
                            append("word")
                        }
                    }
                )

            }

            item(span = { GridItemSpan(numOfRows) }) {
                space(16)
            }

            item(span = { GridItemSpan(numOfRows) }) {
                Text(
                    text = "Password Details",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    fontSize = passwordSize.fontHeaderSize
                )

            }



            item {

                IconLabeledTextField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "Title",
                    text = password.title ?: "",
                    trailingIcon = Icons.Default.ContentCopy,
                    onClick = {
                        onCopyClick(
                            text = password.title,
                            clipboardManager = clipboardManager,
                            coroutineScope = coroutineScope,
                            snackbarHostState = snackbarHostState
                        )
                    }
                )
            }

            item {
                IconLabeledTextField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "Url",
                    text = password.url ?: "",
                    prefix = "https://",
                    trailingIcon = Icons.Default.ContentCopy,
                    onClick = {
                        onCopyClick(
                            text = password.url,
                            clipboardManager = clipboardManager,
                            coroutineScope = coroutineScope,
                            snackbarHostState = snackbarHostState
                        )
                    }
                )
            }
            item(span = { GridItemSpan(numOfRows) }) { space(8) }

            item(span = { GridItemSpan(numOfRows) }) {

                Text(
                    text = "User Details",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    fontSize = passwordSize.fontHeaderSize
                )
            }



            item {

                IconLabeledTextField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "Username",
                    text = password.username ?: "",
                    trailingIcon = Icons.Default.ContentCopy,
                    onClick = {
                        onCopyClick(
                            text = password.username,
                            clipboardManager = clipboardManager,
                            coroutineScope = coroutineScope,
                            snackbarHostState = snackbarHostState
                        )
                    }
                )
            }

            item {

                IconLabeledTextField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "EmailId",
                    text = password.email ?: "",
                    trailingIcon = Icons.Default.ContentCopy,
                    onClick = {
                        onCopyClick(
                            text = password.email,
                            clipboardManager = clipboardManager,
                            coroutineScope = coroutineScope,
                            snackbarHostState = snackbarHostState
                        )
                    }
                )
            }

            item(span = { GridItemSpan(numOfRows) }) {
                space(8)
            }


            item(span = { GridItemSpan(numOfRows) }) {
                Text(
                    text = "Security Keys",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    fontSize = passwordSize.fontHeaderSize
                )
            }


            item {

                IconLabeledTextField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "Password",
                    text = password.password ?: "",
                    trailingIcon = Icons.Default.ContentCopy,
                    onClick = {
                        onCopyClick(
                            text = password.password,
                            clipboardManager = clipboardManager,
                            coroutineScope = coroutineScope,
                            snackbarHostState = snackbarHostState
                        )
                    }
                )
            }

            item {
                IconLabeledTextField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "Pin",
                    text = password.pin ?: "",
                    trailingIcon = Icons.Default.ContentCopy,
                    onClick = {
                        onCopyClick(
                            text = password.pin,
                            clipboardManager = clipboardManager,
                            coroutineScope = coroutineScope,
                            snackbarHostState = snackbarHostState
                        )
                    }
                )
            }

            item(span = { GridItemSpan(numOfRows) }) {
                space(8)
            }

            item(span = { GridItemSpan(numOfRows) }) {

                Text(
                    text = "Tags",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    fontSize = passwordSize.fontHeaderSize
                )
            }

            item(span = { GridItemSpan(numOfRows) }) {
                FlowRow(
                ) {
                    password.tags.forEach {
                        TagCard(it, false) {}
                    }
                }
            }

            item(span = { GridItemSpan(numOfRows) }) {
                space(50)
            }
        }
    }

    AddDataSheet(
        state = state,
        onEvent = onEvent,
        newCard = viewModel.newCard,
        newPassword = viewModel.newPassword
    )

}
