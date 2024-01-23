package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.tabs.DisplayCredentialTab
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.tabs.SettingTab
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.pullRefresh
import dev.materii.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class DisplayPageDisplayClass(
    val appModule: AppModule,
    val onPasswordItemClicked: (Password) -> Unit,
    val onCardItemClicked: (Card) -> Unit,
    val onLogoutUser: (SnackbarHostState, CoroutineScope) -> Unit,
    val onEditProfile: (SnackbarHostState, CoroutineScope) -> Unit,
) : Screen {
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        var isRefreshing by remember {
            mutableStateOf(false)
        }

        var pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
            println("$TAG Refreshing Display Credential Tab")
            isRefreshing = true
            println("$TAG onRefresh IsRefreshing: $isRefreshing")

            MainScope().launch {
                delay(3000)
                isRefreshing = false
                println("$TAG onRefresh after delay IsRefreshing: $isRefreshing")
            }
        })

        println("$TAG IsRefreshing: $isRefreshing")


        val displayCredentialTab =
            DisplayCredentialTab(appModule, onPasswordItemClicked, onCardItemClicked)

        val settingTab = SettingTab(appModule,
            onLogoutUser = {
                onLogoutUser(snackbarHostState, coroutineScope)
            },
            onEditProfile = {
                onEditProfile(snackbarHostState, coroutineScope)
            })

        TabNavigator(tab = displayCredentialTab) {
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                modifier = Modifier
                    .pullRefresh(pullRefreshState),
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth()
                            .padding(0.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        TabNavigationItems(displayCredentialTab)
                        TabNavigationItems(settingTab)
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    CurrentTab()

                    PullRefreshIndicator(
                        refreshing = isRefreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
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
