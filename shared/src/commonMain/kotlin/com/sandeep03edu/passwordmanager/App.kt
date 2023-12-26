package com.sandeep03edu.passwordmanager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sandeep03edu.passwordmanager.core.presentation.AppTheme
import com.sandeep03edu.passwordmanager.manager.authentication.data.getAuthResult
import com.sandeep03edu.passwordmanager.manager.authentication.data.updateServerCards
import com.sandeep03edu.passwordmanager.manager.authentication.data.updateServerPasswords
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.UserAuthentication
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.UserFormFillUpClass
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.DetailedCardDisplayPageClass
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.DetailedPasswordDisplayPageClass
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.DisplayPageDisplayClass
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.PinAuthenticationDisplayClass
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.profile.domain.AuthResponse
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import com.sandeep03edu.passwordmanager.manager.profile.domain.toUserState
import com.sandeep03edu.passwordmanager.manager.utils.data.NetworkEndPoints
import com.sandeep03edu.passwordmanager.manager.utils.data.checkAppPin
import com.sandeep03edu.passwordmanager.manager.utils.data.deleteLoggedInUser
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUser
import com.sandeep03edu.passwordmanager.manager.utils.data.saveLoggedInUser
import com.sandeep03edu.passwordmanager.manager.utils.presentation.DisplaySnackbarToast
import kotlinx.coroutines.launch

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

//        updateServerCards(appModule)
//        updateServerPasswords(appModule)

        val navigator = LocalNavigator.currentOrThrow

        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        AppTheme(
            darkTheme = darkTheme, dynamicColor = dynamicColor
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) {

                    val currUser: UserState? = getLoggedInUser()

                    if (currUser == null) {
                        // User is not logged in
                        var userAuth: AuthResponse? by remember { mutableStateOf(null) }

                        UserAuthentication(
                            onResponse = {
                                userAuth = it
                            })

                        if (userAuth != null) {
                            if (userAuth!!.success) {
                                if (userAuth!!.userExist) {
                                    // User Alr exist
                                    val checkUser: UserState by remember { mutableStateOf(UserState()) }
                                    checkUser.email = userAuth!!.email

                                    // Move to loginPin and appPin authentication
                                    validateUser(checkUser, navigator, appModule)
                                } else {
                                    // User DNE
                                    // Move to registration page
                                    navigator.replace(
                                        UserFormFillUpClass(
                                            url = NetworkEndPoints.registerUser,
                                            labelList = mutableListOf("Sign", "Up"),
                                            buttonLabel = "Create Account",
                                            userState = UserState(email = userAuth!!.email)
                                        ) {
                                            println("$TAG Response:: $it")
                                            if (it.success) {
                                                // Convert to User state
                                                val user = it.toUserState()

                                                // Save the logged in user
                                                saveLoggedInUser(user)

                                                // Move to Display Page
                                                navigator.replace(
                                                    launchLoggedUserDisplayPage(
                                                        navigator,
                                                        appModule
                                                    )
                                                )
                                            } else {
                                                DisplaySnackbarToast(
                                                    snackbarHostState = snackbarHostState,
                                                    scope = coroutineScope,
                                                    message = it.error
                                                )
                                            }
                                        }
                                    )
                                }
                            } else {
                                println("$TAG Error Received")
                                DisplaySnackbarToast(
                                    snackbarHostState = snackbarHostState,
                                    scope = coroutineScope,
                                    message = userAuth!!.error
                                )
                            }
                        }
                    } else {
                        // Move to display page for logged in user
                        navigator.push(
                            PinAuthenticationDisplayClass(
                                label = "Login pin",
                                pinLength = 4,
                                onComplete = {

                                    // TODO : Uncomment this
                                    //                                if (checkLoginPin(it)) {
                                    navigator.replace(
                                        launchLoggedUserDisplayPage(navigator, appModule)
                                    )
                                    //                                }
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}


fun launchLoggedUserDisplayPage(navigator: Navigator, appModule: AppModule): Screen {
    return DisplayPageDisplayClass(
        appModule,
        onPasswordItemClicked = { selectedPassword ->
            val user = getLoggedInUser()
            if (user != null) {
                navigator.push(
                    PinAuthenticationDisplayClass(
                        pinLength = 6,
                        label = "App Pin",
                        onComplete = { appPin ->
                            navigator.pop()
                            if (checkAppPin(appPin)) {
                                // Move to Detailed password page if login passes
                                navigator.push(
                                    DetailedPasswordDisplayPageClass(
                                        appModule,
                                        selectedPassword
                                    )
                                )
                            }
                        })
                )
            }
        },
        onCardItemClicked = { selectedCard ->
            val user = getLoggedInUser()
            if (user != null) {
                navigator.push(
                    PinAuthenticationDisplayClass(
                        pinLength = 6,
                        label = "App Pin",
                        onComplete = { appPin ->
                            navigator.pop()
                            if (checkAppPin(appPin)) {
                                navigator.push(
                                    // Move to Detailed card page if login passes
                                    DetailedCardDisplayPageClass(
                                        appModule,
                                        selectedCard
                                    )
                                )
                            }
                        })
                )
            }
        },
        onLogoutUser = {
            deleteLoggedInUser()
            navigator.popUntilRoot()
            navigator.replace(
                AppHomeLayout(
                    darkTheme = false,
                    dynamicColor = true,
                    appModule = appModule
                )
            )

        },
        onEditProfile = {
            val userState = getLoggedInUser()!!
            userState.loginPin = ""
            userState.appPin = ""
            navigator.push(
                UserFormFillUpClass(
                    url = NetworkEndPoints.updateUser,
                    labelList = mutableListOf("Edit", "Profile"),
                    buttonLabel = "Update Account",
                    userState = userState,
                    onRegister = {
                        if (it.success) {
                            val user = it.toUserState()

                            // Save the logged in user
                            saveLoggedInUser(user)
                        } else {
                            // TODO : Toast error message
                        }
                        navigator.pop()
                    }
                )
            )
        }
    )
}

fun validateUser(checkUser: UserState, navigator: Navigator, appModule: AppModule) {
    navigator.push(
        PinAuthenticationDisplayClass(
            label = "Login pin",
            pinLength = 4,
            onComplete = {
                checkUser.loginPin = it
                navigator.pop()

                navigator.push(
                    PinAuthenticationDisplayClass(
                        label = "App pin",
                        pinLength = 6,
                        onComplete = {
                            checkUser.appPin = it

                            println("$TAG CheckUser: $checkUser")
                            navigator.pop()

                            getAuthResult(
                                url = NetworkEndPoints.loginUser,
                                userState = checkUser,
                                result = { authResponse ->
                                    println("$TAG Login Response : $authResponse")
                                    if (authResponse.success) {
                                        // Credentials Matched!!
                                        val userState = authResponse.toUserState()

                                        // Save logged in User
                                        saveLoggedInUser(userState)

                                        // Fetch Already saved data from server
                                        updateServerCards(appModule)

                                        updateServerPasswords(appModule)

                                        navigator.replace(
                                            launchLoggedUserDisplayPage(navigator, appModule)
                                        )
                                    } else {
                                        // Credentials not matched!!
                                        // TODO : Show error message
                                    }
                                }
                            )
                        }
                    )
                )

            }
        )
    )
}


@Composable
fun space(height: Int = 0, width: Int = 0) {
    Spacer(modifier = Modifier.height(height.dp).width(width.dp))
}

