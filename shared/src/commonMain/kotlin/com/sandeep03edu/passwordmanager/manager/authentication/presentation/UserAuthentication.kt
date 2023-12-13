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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appmattus.crypto.Algorithm
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CardButton
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CircularImage
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CountryCodePicker
import com.sandeep03edu.passwordmanager.manager.utils.presentation.OtpTextField
import com.sandeep03edu.passwordmanager.paintResource
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.PhoneAuthProvider
import dev.gitlive.firebase.auth.auth
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray

@Composable
fun UserAuthentication() {
    var phoneNumber: String by rememberSaveable { mutableStateOf("") }
    var fullPhoneNumber: String by rememberSaveable { mutableStateOf("") }
    var isNumberValid: Boolean by rememberSaveable { mutableStateOf(false) }
    var otpValue: String by remember { mutableStateOf("") }
    var otpSent: Boolean by remember { mutableStateOf(false) }
    var isPhoneNumberValid: Boolean by remember { mutableStateOf(false) }
    var isOtpValid: Boolean by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularImage(
            painter = paintResource(SharedRes.images.avatar),

            )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Phone Verification", style = TextStyle(
                fontWeight = FontWeight.ExtraBold, fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We need to register your phone number before getting started!",
            style = TextStyle(
                fontWeight = FontWeight.Thin, fontSize = 16.sp
            ),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!otpSent) {
            EnterPhoneNumberView(
                onValueChange = { code, phone, valid ->
                    isPhoneNumberValid = valid
                    println("Helllo Value changed to $code - $phone And $valid")
                },
                isPhoneNumberValid = isPhoneNumberValid,
                sendCode = {
                    // TODO: Send OTP
                }
            )
        } else {
            EnterOtpField(
                otpValue = otpValue,
                onOtpTextChange = { value, otpValidity ->
                    otpValue = value
                    isOtpValid = otpValidity
                    println("OTP Val: $otpValue && Status: $otpValidity")
                },
                isOtpValid = isOtpValid
            )
        }
    }


}

suspend fun FirebaseAuthProcess() {
    val auth: FirebaseAuth = Firebase.auth

    val phoneAuthProvider = PhoneAuthProvider(auth)


//    val credential: PhoneAuthCredential = phoneAuthProvider.credential()


//    val phoneVerificationProvider = object : PhoneVerificationProvider {
//        override val verifier: firebase.auth.ApplicationVerifier
//            get() = TODO()

//        override suspend fun getVerificationCode(verificationId: String): String {
//            TODO()
//        }
//    }

//    println("Firebase VerifyPhone Started")
//    val authCredential: AuthCredential = phoneAuthProvider.verifyPhoneNumber("+918178538456",phoneVerificationProvider)
//    println("Firebase AuthCred: $authCredential")


//    auth.signInWithCredential(authCredential)

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
