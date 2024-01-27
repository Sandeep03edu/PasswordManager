package com.sandeep03edu.passwordmanager.manager.credentials.presentation.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserEmail
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserName
import com.sandeep03edu.passwordmanager.manager.utils.presentation.AlertDialogBox
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CircularImage
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class SettingTab(
    var appModule: AppModule,
    var onLogoutUser: () -> Unit,
    var onEditProfile: () -> Unit,
) : Tab {

    @Composable
    override fun Content() {
        val windowSizeClass = calculateWindowSizeClass()

        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                SettingPageCompactDisplay(appModule, onLogoutUser, onEditProfile)
            }

            WindowWidthSizeClass.Medium -> {
                SettingPageMediumExpandedDisplay(appModule, onLogoutUser, onEditProfile)
            }

            WindowWidthSizeClass.Expanded -> {
                SettingPageMediumExpandedDisplay(appModule, onLogoutUser, onEditProfile)
            }
        }

    }


    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Person)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Setting",
                    icon = icon
                )
            }
        }

}


@Composable
fun SettingPageCompactDisplay(
    appModule: AppModule,
    onLogoutUser: () -> Unit,
    onEditProfile: () -> Unit,
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            space(16)
        }
        item {
            CircularImage(
                painter = paintResource(SharedRes.images.avatar),
                modifier = Modifier.size(150.dp)
            )
            space(4)
        }

        item {
            Text(
                text = getLoggedInUserName(),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            space(4)
        }

        item {
            Text(
                text = getLoggedInUserEmail(),
                style = TextStyle(
                    fontSize = 18.sp,
                )
            )
            space(16)
        }

        item {
            SettingOptions(Icons.Default.Person, "Edit Profile") {
                onEditProfile()
            }
            space(8)
        }

        item {
            SettingOptions(Icons.Default.Person, "Privacy Policy") {
                // TODO : Display Dialog with Privacy Policy
            }
            space(8)
        }
        item {
            var displayLogoutDialog by remember { mutableStateOf(false) }
            SettingOptions(Icons.Default.Person, "Logout") {
                displayLogoutDialog = true
            }

            if (displayLogoutDialog) {
                Dialog(
                    content = {
                        AlertDialogBox(
                            title = "Do you want to logout?",
                            onYesClick = {
                                onLogoutUser()
                                displayLogoutDialog = false
                            },
                            onNoClick = {
                                displayLogoutDialog = false
                            }
                        )
                    },
                    onDismissRequest = {
                        displayLogoutDialog = false
                    },

                    )
            }
            space(8)
        }
    }
}

@Composable
fun SettingPageMediumExpandedDisplay(
    appModule: AppModule,
    onLogoutUser: () -> Unit,
    onEditProfile: () -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyColumn (
            modifier = Modifier.weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            item {
                space(16)
            }
            item {
                CircularImage(
                    painter = paintResource(SharedRes.images.avatar),
                    modifier = Modifier.size(150.dp)
                )
                space(4)
            }

            item {
                Text(
                    text = getLoggedInUserName(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                space(4)
            }

            item {
                Text(
                    text = getLoggedInUserEmail(),
                    style = TextStyle(
                        fontSize = 18.sp,
                    )
                )
                space(16)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                SettingOptions(Icons.Default.Person, "Edit Profile") {
                    onEditProfile()
                }
                space(8)
            }

            item {
                SettingOptions(Icons.Default.Person, "Privacy Policy") {
                    // TODO : Display Dialog with Privacy Policy
                }
                space(8)
            }
            item {
                var displayLogoutDialog by remember { mutableStateOf(false) }
                SettingOptions(Icons.Default.Person, "Logout") {
                    displayLogoutDialog = true
                }

                if (displayLogoutDialog) {
                    Dialog(
                        content = {
                            AlertDialogBox(
                                title = "Do you want to logout?",
                                onYesClick = {
                                    onLogoutUser()
                                    displayLogoutDialog = false
                                },
                                onNoClick = {
                                    displayLogoutDialog = false
                                }
                            )
                        },
                        onDismissRequest = {
                            displayLogoutDialog = false
                        },

                        )
                }
                space(8)
            }
        }
    }
}

@Composable
fun SettingOptions(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(0),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp)

        ) {
            Image(
                imageVector = icon,
                contentDescription = null
            )

            space(width = 8)

            Text(
                text = label,
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
    }
}
