package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.tabs.DisplayCredentialTab
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.tabs.SettingTab
import com.sandeep03edu.passwordmanager.manager.di.AppModule


data class DisplayPageDisplayClass(
    val appModule: AppModule,
    val onPasswordItemClicked: (Password) -> Unit,
    val onCardItemClicked: (Card) -> Unit,
) : Screen {

    @Composable
    override fun Content() {
        val displayCredentialTab =
            DisplayCredentialTab(appModule, onPasswordItemClicked, onCardItemClicked)
        TabNavigator(tab = displayCredentialTab) {
            Scaffold(
                bottomBar = {

                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth()
                            .padding(0.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        TabNavigationItems(displayCredentialTab)
                        TabNavigationItems(SettingTab)
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    CurrentTab()
                }
            }
        }


    }

}

@Composable
fun RowScope.TabNavigationItems(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = {
            tabNavigator.current = tab
        },
        icon = {
            Icon(tab.options.icon!!, contentDescription = tab.options.title)
        },
        label = {
            Text(text = tab.options.title)
        },
    )
}
