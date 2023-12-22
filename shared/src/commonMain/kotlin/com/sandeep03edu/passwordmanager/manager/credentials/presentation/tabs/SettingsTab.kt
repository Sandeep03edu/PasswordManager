package com.sandeep03edu.passwordmanager.manager.credentials.presentation.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserEmail
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserName
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CircularImage
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space


object SettingTab : Tab {
    @Composable
    override fun Content() {
        SettingPageDisplay()
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
fun SettingPageDisplay() {
    val navigator = LocalNavigator.currentOrThrow

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
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
                // TODO : Move to Edit Profile Page
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
            SettingOptions(Icons.Default.Person, "Logout") {
                // TODO : Logout user and delete user data
            }
            space(8)
        }
    }
}

@Composable
fun SettingOptions(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()
        .background(MaterialTheme.colorScheme.background),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .background(MaterialTheme.colorScheme.secondary),
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
                    .clickable {
                        onClick()
                    }
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
}