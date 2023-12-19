package com.sandeep03edu.passwordmanager.manager.authentication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.manager.authentication.data.getAuthResult
import com.sandeep03edu.passwordmanager.manager.profile.domain.AuthResponse
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CardButton
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CircularImage
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconEditNumberField
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconEditTextField
import com.sandeep03edu.passwordmanager.paintResource
import kotlinx.coroutines.launch

data class UserFormFillUpClass(
    var email: String,
    var onRegister: (AuthResponse) -> Unit,
) : Screen {
    @Composable
    override fun Content() {
        UserFormFillUp(email,onRegister)
    }

}

@Composable
fun UserFormFillUp(
    email: String,
    onRegister: (AuthResponse) -> Unit,
) {
    val TAG = "UserFormFillUp";
    var user by remember { mutableStateOf(UserState(email = email)) }
    var isLoading by remember { mutableStateOf(false) }

    var validator by remember { mutableStateOf(UserValidation()) }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 40.sp
                            )
                        ) {
                            append("S")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        ) {
                            append("ign ")
                        }

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 40.sp
                            )
                        ) {
                            append("U")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        ) {
                            append("p")
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                CircularImage(
                    painter = paintResource(SharedRes.images.avatar),
                    modifier = Modifier.size(100.dp),
                    onClick = {
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                IconEditTextField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "First Name",
                    text = user.firstName,
                    onTextChange = {
                        user = user.copy(
                            firstName = it
                        )
                    },
                    errorMessage = validator.firstNameError,
                    enabled = !isLoading
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                IconEditTextField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "Last Name",
                    text = user.lastName,
                    onTextChange = {
                        user = user.copy(
                            lastName = it
                        )
                    },
                    errorMessage = validator.lastNameError,
                    enabled = !isLoading
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                IconEditNumberField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "Login Pin",
                    text = user.loginPin,
                    onTextChange = {
                        user = user.copy(
                            loginPin = it
                        )
                    },
                    maxLength = 4,
                    errorMessage = validator.loginPinError,
                    enabled = !isLoading
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                IconEditNumberField(
                    leadingIcon = Icons.Rounded.Person,
                    label = "App Pin",
                    text = user.appPin,
                    onTextChange = {
                        user = user.copy(
                            appPin = it
                        )
                    },
                    maxLength = 6,
                    errorMessage = validator.appPinError,
                    enabled = !isLoading
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                CardButton(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    text = "Create Account",
                    clickEnabled = !isLoading,
                    onClick = {
                        // Check All fields
                        validator = validateFields(
                            user.firstName,
                            user.lastName,
                            user.loginPin,
                            user.appPin
                        )

                        val allError = listOfNotNull(
                            validator.firstNameError,
                            validator.lastNameError,
                            validator.loginPinError,
                            validator.appPinError
                        )

                        if (allError.isEmpty()) {
                            println("$TAG No error found!!")
                            isLoading = true

                            getAuthResult(
                                url = "/api/auth/register",
                                result = {
                                    println("$TAG Auth From Register:: $it")
                                    onRegister(it)
                                    if (it.success) {
                                        // Registered Successfully!!
                                        onRegister(it)
                                    } else {
                                        // Registration failed!!
                                        // TODO : Display Error Message
                                    }
                                },
                                userState = user
                            )
                        }
                    }
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        }

    }
}


fun validateFields(
    firstName: String,
    lastName: String,
    loginPin: String,
    appPin: String,
): UserValidation {

    var validator = UserValidation()

    if (firstName.isEmpty()) {
        validator.firstNameError = "First Name can't be empty"
    }

    if (loginPin.length != 4) {
        validator.loginPinError = "Login pin should contain 4 numbers"
    }

    if (appPin.length != 6) {
        validator.appPinError = "App pin should contain 6 numbers"
    }

    return validator
}

data class UserValidation(
    var firstNameError: String? = null,
    var lastNameError: String? = null,
    var loginPinError: String? = null,
    var appPinError: String? = null,
)