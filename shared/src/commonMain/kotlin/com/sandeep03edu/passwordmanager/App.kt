package com.sandeep03edu.passwordmanager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sandeep03edu.passwordmanager.core.data.Background
import com.sandeep03edu.passwordmanager.core.data.Main
import com.sandeep03edu.passwordmanager.core.presentation.AppTheme
import com.sandeep03edu.passwordmanager.manager.authentication.data.getAuthResult
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.UserAuthentication
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.DetailedCardDisplayPageClass
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.DetailedPasswordDisplayPageClass
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.DisplayPageDisplayClass
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.PinAuthenticationDisplayClass
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.profile.domain.AuthResponse
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUser
import com.sandeep03edu.passwordmanager.manager.utils.domain.hashingAlgorithm
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonObject

val TAG = "AppTag"

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
) {

    Navigator(
        AppHomeLayout(darkTheme, dynamicColor, appModule),
    )
}

data class AppHomeLayout(
    val darkTheme: Boolean,
    val dynamicColor: Boolean,
    val appModule: AppModule,
) : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

//        var viewModel = navigator.rememberNavigatorScreenModel { CredentialViewModel(appModule.credentialDataSource) }
//        val viewModel = rememberScreenModel { CredentialViewModel(appModule.credentialDataSource) }

        AppTheme(
            darkTheme = darkTheme, dynamicColor = dynamicColor
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                val currUser = Firebase.auth.currentUser
                if (currUser == null) {
                    UserAuthentication()
                } else {
                    // TODO : Remove
                    navigator.push(
                        DisplayPageDisplayClass(
                            appModule,
                            onPasswordItemClicked = { selectedPassword ->
                                val user = getLoggedInUser()
                                if (user != null) {
                                    navigator.push(
                                        PinAuthenticationDisplayClass(
                                            user,
                                            "App Pin",
                                            onComplete = { result ->
                                                navigator.pop()
                                                // TODO : Remove comment
//                                                if (result) {
                                                // Move to Detailed password page if login passes
                                                navigator.push(
                                                    DetailedPasswordDisplayPageClass(
                                                        appModule,
                                                        selectedPassword
                                                    )
                                                )
//                                                }
                                            })
                                    )
                                }
                            },
                            onCardItemClicked = { selectedCard ->
                                val user = getLoggedInUser()
                                if (user != null) {
                                    navigator.push(
                                        PinAuthenticationDisplayClass(
                                            user,
                                            "App Pin",
                                            onComplete = { result ->
                                                navigator.pop()
//                                                if (result) {
                                                // TODO : Move to Detailed card page

                                                // Navigate to Detailed card page if login passes
                                                navigator.push(
                                                    DetailedCardDisplayPageClass(
                                                        appModule,
                                                        selectedCard
                                                    )
                                                )
//                                                }
                                            })
                                    )
                                }
                            }
                        )
                    )


// TODO : Uncomment
                    /*
                                    val usr = getLoggedInUser()
                                    if (usr == null) {
                    //                    val phoneNum = currUser?.phoneNumber
                                        val phoneNum = "+918178538456"

                                        UserFormFillUp(phoneNum)
                                    } else {
                                        // Display Page
                                        DisplayPageDisplay(state, onEvent, viewModel.newCard, viewModel.newPassword)

                                        // Pin Authentication
                                        */
                    /*
                                                            PinAuthenticationDisplay(usr);
                                        *//*


                }
*/
                }
            }
        }
    }

    private fun apiCallingTest(
    ) {
       getAuthResult(
           url = "/api/auth/emailExist"
       ){
           println("$TAG Auth Respo:: $it")
       }

    }

    private fun testHashFunction() {
        println("$TAG Hash of Sandeep is ${hashingAlgorithm("Sandeep")}")
        println("$TAG Hash of Ravi Teja is ${hashingAlgorithm("Ravi Teja")}")
        println("$TAG Hash of Kaveri is ${hashingAlgorithm("Kaveri")}")
        println("$TAG Hash of alakh Mishra Pandey is ${hashingAlgorithm("alakh Mishra Pandey")}")
    }

}


@Composable
fun space(height: Int = 0, width: Int = 0) {
    Spacer(modifier = Modifier.height(height.dp).width(width.dp))
}

