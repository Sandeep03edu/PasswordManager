package com.sandeep03edu.passwordmanager.manager.authentication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.manager.authentication.data.getAuthResult
import com.sandeep03edu.passwordmanager.manager.profile.domain.AuthResponse
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import com.sandeep03edu.passwordmanager.manager.utils.data.getPrivacyPolicyDescription
import com.sandeep03edu.passwordmanager.manager.utils.presentation.AlertDialogBox
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CardButton
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CircularImage
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconEditTextField
import com.sandeep03edu.passwordmanager.manager.utils.presentation.OtpTextField
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space
import com.sandeep03edu.passwordmanager.ui.theme.getBackgroundColor
import com.sandeep03edu.passwordmanager.ui.theme.getCardSubmitButtonBackground
import com.sandeep03edu.passwordmanager.ui.theme.getDisabledButtonBackground
import com.sandeep03edu.passwordmanager.ui.theme.getLinkBackground
import com.sandeep03edu.passwordmanager.ui.theme.getTextColor

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun UserAuthentication(
    onResponse: (AuthResponse) -> Unit,
    loading: Boolean = false,
) {
    var emailId: String by rememberSaveable { mutableStateOf("") }
    var isEmailIdValid: Boolean by remember { mutableStateOf(isValidEmail(emailId)) }
    var isLoading by remember { mutableStateOf(loading) }
    var checkBoxChecked by remember { mutableStateOf(false) }
    var showPrivacyAlertBox by remember { mutableStateOf(false) }

    var numOfRows = 1

    val windowSizeClass = calculateWindowSizeClass()

    when (windowSizeClass.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            numOfRows = 2
        }

        WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> {
            numOfRows = 1
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(numOfRows),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(getBackgroundColor())
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularImage(
                    painter = paintResource(SharedRes.images.avatar),
                )

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier,
                    )
                }

                space(16)

                Text(
                    text = "Email Verification", style = TextStyle(
                        fontWeight = FontWeight.ExtraBold, fontSize = 20.sp
                    ),
                    color = getTextColor()
                )

                space(8)

                Text(
                    text = "We need to authenticate your email id before getting started!!",
                    style = TextStyle(
                        fontWeight = FontWeight.Thin, fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center,
                    color = getTextColor()
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconEditTextField(
                    leadingIcon = Icons.Default.Person,
                    label = "Email Id",
                    text = emailId,
                    onTextChange = {
                        emailId = it
                        isEmailIdValid = isValidEmail(it)
                    },
                    enabled = !isLoading,
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkBoxChecked,
                        onCheckedChange = {
                            checkBoxChecked = it
                        }
                    )

                    val pvp = "privacy policy"
                    val privacyPolicyString = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 16.sp, color = getTextColor())) {
                            append("I accept the ")
                        }

                        withStyle(
                            style = SpanStyle(
                                color = getLinkBackground(),
                                fontSize = 16.sp
                            )
                        ) {
                            pushStringAnnotation(tag = pvp, annotation = pvp)
                            append(pvp)
                        }
                    }

                    ClickableText(
                        text = privacyPolicyString,
                        onClick = { offset ->
                            privacyPolicyString.getStringAnnotations(offset, offset)
                                .firstOrNull()?.let { span ->
                                    showPrivacyAlertBox = true
                                }
                        },

                    )
                    if (showPrivacyAlertBox) {
                        Dialog(
                            content = {
                                AlertDialogBox(
                                    title = "",
                                    description = getPrivacyPolicyDescription()
                                )
                            },
                            onDismissRequest = { showPrivacyAlertBox = false }
                        )

                    }
                }

                space(8)

                CardButton(
                    text = "Proceed",
                    clickEnabled = isProceedButtonEnable(
                        isLoading,
                        isEmailIdValid,
                        checkBoxChecked
                    ),
                    onClick = {
                        isLoading = true
                        getAuthResult(
                            "/api/auth/emailExist",
                            userState = UserState(email = emailId)
                        ) {
                            println("$TAG Auth from email:: $it")
                            isLoading = false
                            onResponse(it)
                        }
                    },
                    backgroundColor = if (isProceedButtonEnable(
                            isLoading,
                            isEmailIdValid,
                            checkBoxChecked
                        )
                    ) getCardSubmitButtonBackground() else getDisabledButtonBackground()
                )

                space(8)

            }
        }
    }


}

fun isProceedButtonEnable(
    isLoading: Boolean,
    isEmailIdValid: Boolean,
    checkBoxChecked: Boolean,
): Boolean {
    return !isLoading && isEmailIdValid && checkBoxChecked
}

fun isValidEmail(email: String): Boolean {
    if (email.trim().isEmpty()) {
        return false
    }
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return email.matches(emailRegex.toRegex())
}

@Composable
fun EnterOtpField(
    onOtpTextChange: (String, Boolean) -> Unit,
    otpValue: String,
    isOtpValid: Boolean,
) {
    OtpTextField(
        otpText = otpValue,
        onOtpTextChange =
        { value, otpInputFilled ->
            onOtpTextChange(value, otpInputFilled)
        },
    )

    Spacer(modifier = Modifier.height(24.dp))

    var backgroundColor = MaterialTheme.colorScheme.primary
    if (!isOtpValid) {
        backgroundColor = MaterialTheme.colorScheme.error
    }

    CardButton(
        text = "Verify Code",
        backgroundColor = backgroundColor,
        onClick = {

        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Edit phone number?",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable {

                },
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal
            ),
        )
    }
}



