package com.sandeep03edu.passwordmanager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.sandeep03edu.passwordmanager.core.presentation.AppTheme
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.PinAuthentication
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.UserAuthentication
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.CredentialViewModel
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.DisplayPageDisplay
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

val TAG = "AppTag"

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
) {
    Navigator(AppHomeLayout(darkTheme, dynamicColor, appModule))
}

data class AppHomeLayout(
    val darkTheme: Boolean,
    val dynamicColor: Boolean,
    val appModule: AppModule,
) : Screen {
    @Composable
    override fun Content() {
        AppTheme(
            darkTheme = darkTheme, dynamicColor = dynamicColor
        ) {
            val viewModel = getViewModel(
                key = "Credential-View_Model",
                factory = viewModelFactory {
                    CredentialViewModel(appModule.credentialDataSource)
                }
            )


            val state by viewModel.state.collectAsState()
            val onEvent = viewModel::onEvent

            Box(
                modifier = Modifier.fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                val currUser = Firebase.auth.currentUser
                if (currUser != null) {
                    UserAuthentication()
                } else {
                    // TODO : Remove
                    DisplayPageDisplay(
                        state,
                        onEvent,
                        viewModel.newCard,
                        viewModel.newPassword,
                        viewModel
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

}


@Composable
fun space(height: Int = 0, width: Int = 0) {
    Spacer(modifier = Modifier.height(height.dp).width(width.dp))
}

