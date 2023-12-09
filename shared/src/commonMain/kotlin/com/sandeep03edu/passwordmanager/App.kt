package com.sandeep03edu.passwordmanager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import com.sandeep03edu.passwordmanager.core.presentation.AppTheme
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.PinAuthentication
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.UserAuthentication
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.CredentialViewModel
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.DisplayPageDisplay
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.DisplayPageDisplayClass
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.PinAuthenticationDisplayClass
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.DetailedPasswordDisplayPage
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.DetailedPasswordDisplayPageClass
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val TAG = "AppTag"

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
) {
    Navigator(AppHomeLayout(darkTheme, dynamicColor, appModule),
//        onBackPressed = { currentScreen ->
//            false // won't pop the current screen
//            // true will pop, default behavior
//        }
    )
}

data class AppHomeLayout(
    val darkTheme: Boolean,
    val dynamicColor: Boolean,
    val appModule: AppModule,
) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
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
                if (currUser != null) {
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
                                                if (result) {
                                                    // TODO : Move to Detailed card page
                                                }
                                            })
                                    )
                                }
                            }
                        )
                    )

//                    DisplayPageDisplay(
//                        state,
//                        onEvent,
//                        viewModel.newCard,
//                        viewModel.newPassword,
//                        viewModel,
//                        onPasswordItemClicked = {
//                            val usr = getLoggedInUser()
//                            println("$TAG Userrr: $usr")
//                            if(usr!=null) {
//                                navigator.push(PinAuthenticationDisplayClass(usr, "App Pin"))
//                            }
//                        }
//                    )

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

}


@Composable
fun space(height: Int = 0, width: Int = 0) {
    Spacer(modifier = Modifier.height(height.dp).width(width.dp))
}

