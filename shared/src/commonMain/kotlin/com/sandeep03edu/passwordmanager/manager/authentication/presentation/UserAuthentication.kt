package com.sandeep03edu.passwordmanager.manager.authentication.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CardButton
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CircularImage
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CountryCodePicker
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconEditTextField
import com.sandeep03edu.passwordmanager.manager.utils.presentation.OtpTextField
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space

@Composable
fun UserAuthentication() {
    var emailId: String by rememberSaveable { mutableStateOf("") }
    var isEmailIdValid: Boolean by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularImage(
            painter = paintResource(SharedRes.images.avatar),
        )

        space(16)

        Text(
            text = "Email Verification", style = TextStyle(
                fontWeight = FontWeight.ExtraBold, fontSize = 20.sp
            )
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
            textAlign = TextAlign.Center
        )

        space(24)


        IconEditTextField(
            leadingIcon = Icons.Default.Person,
            label = "Email Id",
            text = emailId,
            onTextChange = {
                emailId = it
                isEmailIdValid = isValidEmail(it)

            },
        )

        space(8)

        CardButton(
            text = "Proceed",
            clickEnabled = isEmailIdValid,
            onClick = {

            },
            backgroundColor = if (isEmailIdValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )

        space(8)

    }
}

fun isValidEmail(email: String): Boolean {
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


@Composable
fun EnterPhoneNumberView(
    onValueChange: (String, String, Boolean) -> Unit,
    isPhoneNumberValid: Boolean,
    sendCode: () -> Unit,
) {
    CountryCodePicker(onValueChange = { code, phone, valid ->
        onValueChange(code, phone, valid)
    })

    var backgroundColor = MaterialTheme.colorScheme.primary
    if (!isPhoneNumberValid) {
        backgroundColor = MaterialTheme.colorScheme.error
    }

    Spacer(modifier = Modifier.height(16.dp))

    CardButton(
        text = "Send Code",
        clickEnabled = isPhoneNumberValid,
        onClick = {
            sendCode()
        },
        backgroundColor = backgroundColor
    )
}
