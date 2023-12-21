package com.sandeep03edu.passwordmanager.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.sandeep03edu.passwordmanager.App
import com.sandeep03edu.passwordmanager.manager.di.AppModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(this)
        setContent {



            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false,
                appModule = AppModule(LocalContext.current.applicationContext),
            )

        }
    }
}


