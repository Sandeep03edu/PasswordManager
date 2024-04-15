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
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.lifecycle.LocalNavigatorScreenLifecycleProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.sandeep03edu.passwordmanager.core.presentation.AppTheme
import com.sandeep03edu.passwordmanager.manager.authentication.data.getAuthResult
import com.sandeep03edu.passwordmanager.manager.authentication.data.restartApiCall
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
import com.sandeep03edu.passwordmanager.manager.utils.data.checkLoginPin
import com.sandeep03edu.passwordmanager.manager.utils.data.deleteLoggedInUser
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUser
import com.sandeep03edu.passwordmanager.manager.utils.data.saveLoggedInUser
import com.sandeep03edu.passwordmanager.manager.utils.presentation.DisplaySnackbarToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val TAG = "AppTag"

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
) {
    Navigator(
        rememberSaveable { AppHomeLayout(darkTheme, dynamicColor, appModule) }
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
                                println("$TAG UserAuth: $it")
                            })

                        if (userAuth != null) {
                            if (userAuth!!.success) {
                                if (userAuth!!.userExist) {
                                    // User Alr exist
                                    val checkUser: UserState by remember {
                                        mutableStateOf(
                                            UserState()
                                        )
                                    }
                                    checkUser.email = userAuth!!.email

                                    // Move to loginPin and appPin authentication
                                    validateUser(
                                        checkUser,
                                        navigator,
                                        appModule
                                    )
                                } else {
                                    // User DNE
                                    // Move to registration page
                                    navigator.push(
                                        UserFormFillUpClass(
                                            url = NetworkEndPoints.registerUser,
                                            labelList = mutableListOf("Sign", "Up"),
                                            buttonLabel = "Create Account",
                                            userState = UserState(email = userAuth!!.email)
                                        ) { it, snackbarHostState, coroutineScope ->
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
                                                    coroutineScope = coroutineScope,
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
                                    coroutineScope = coroutineScope,
                                    message = userAuth!!.error
                                )
                            }
                        }
                    } else {
                        // TODO : Remove verification skip
                        if(1==1) {
                            navigator.replace(
                                launchLoggedUserDisplayPage(
                                    navigator,
                                    appModule
                                )
                            )
                        }
                        else {
                            // Move to display page for logged in user
                            navigator.push(
                                PinAuthenticationDisplayClass(
                                    label = "Login pin",
                                    pinLength = 4,
                                    onComplete = { it, snackbarHostState, coroutineScope ->

                                        if (checkLoginPin(it)) {
                                            navigator.replace(
                                                launchLoggedUserDisplayPage(
                                                    navigator,
                                                    appModule
                                                )
                                            )


                                        } else {
                                            println("$TAG Wrong Login Pin!!")
                                            DisplaySnackbarToast(
                                                snackbarHostState,
                                                coroutineScope,
                                                "Wrong Login Pin!!"
                                            )
                                        }

                                    }
                                )
                            )
                        }
                    }
                }
            }
        }
    }


}

fun launchLoggedUserDisplayPage(
    navigator: Navigator,
    appModule: AppModule,
): Screen {
    return DisplayPageDisplayClass(
        appModule,
        onPasswordItemClicked = { selectedPassword ->
            val user = getLoggedInUser()
            if (user != null) {
                navigator.push(
                    PinAuthenticationDisplayClass(
                        pinLength = 6,
                        label = "App Pin",
                        onComplete = { appPin, snackbarHostState, coroutineScope ->
                            if (checkAppPin(appPin)) {
                                // Move to Detailed password page if login passes
                                navigator.pop()
                                navigator.push(
                                    DetailedPasswordDisplayPageClass(
                                        appModule,
                                        selectedPassword
                                    )
                                )
                            } else {
                                DisplaySnackbarToast(
                                    snackbarHostState = snackbarHostState,
                                    coroutineScope = coroutineScope,
                                    message = "Wrong App pin"
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
                        onComplete = { appPin, snackbarHostState, coroutineScope ->
                            if (checkAppPin(appPin)) {
                                println("$TAG Check App Pin passed!!")
                                navigator.pop()
                                navigator.push(
                                    // Move to Detailed card page if login passes
                                    DetailedCardDisplayPageClass(
                                        appModule,
                                        selectedCard
                                    )
                                )
                            } else {
                                DisplaySnackbarToast(
                                    snackbarHostState = snackbarHostState,
                                    coroutineScope = coroutineScope,
                                    message = "Wrong App pin"
                                )
                            }
                        })
                )
            }
        },
        onLogoutUser = { snackbarHostState, coroutineScope ->
            DisplaySnackbarToast(
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope,
                message = "Logging out!!"
            )
            coroutineScope.launch {
                delay(1000L)
            }

            deleteLoggedInUser(appModule)


            navigator.popUntilRoot()
            navigator.replace(
                AppHomeLayout(
                    darkTheme = false,
                    dynamicColor = true,
                    appModule = appModule
                )
            )

        },
        onEditProfile = { snackbarHostState, coroutineScope ->
            val userState = getLoggedInUser()!!
            userState.loginPin = ""
            userState.appPin = ""
            navigator.push(
                UserFormFillUpClass(
                    url = NetworkEndPoints.updateUser,
                    labelList = mutableListOf("Edit", "Profile"),
                    buttonLabel = "Update Account",
                    userState = userState,
                    onRegister = { it, snackbarHostState, coroutineScope ->
                        if (it.success) {
                            val user = it.toUserState()

                            // Save the logged in user
                            saveLoggedInUser(user)

                            // Display Toast
                            DisplaySnackbarToast(
                                snackbarHostState = snackbarHostState,
                                coroutineScope = coroutineScope,
                                message = "Edit profile successful"
                            )

                            // Moving back
                            navigator.pop()
                        } else {
                            DisplaySnackbarToast(
                                snackbarHostState = snackbarHostState,
                                coroutineScope = coroutineScope,
                                message = it.error
                            )
                        }
                    }
                )
            )
        }
    )
}

fun validateUser(
    checkUser: UserState,
    navigator: Navigator,
    appModule: AppModule,
) {
    navigator.push(
        PinAuthenticationDisplayClass(
            label = "Login pin",
            pinLength = 4,
            onComplete = { loginPin, _, _ ->
                checkUser.loginPin = loginPin
                navigator.pop()

                navigator.push(
                    PinAuthenticationDisplayClass(
                        label = "App pin",
                        pinLength = 6,
                        onComplete = { it, snackbarHostState, coroutineScope ->
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
                                            launchLoggedUserDisplayPage(
                                                navigator,
                                                appModule
                                            )
                                        )
                                    } else {
                                        // Credentials not matched!!
                                        DisplaySnackbarToast(
                                            snackbarHostState = snackbarHostState,
                                            coroutineScope = coroutineScope,
                                            message = authResponse.error
                                        )
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

