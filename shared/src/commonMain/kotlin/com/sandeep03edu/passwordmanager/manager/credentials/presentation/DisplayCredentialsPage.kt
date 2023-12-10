package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.PasswordSecureHalfDisplay
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.PasswordTagCard
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.SecureHalfCardDisplay
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.utils.data.getPasswordTagsWithIcons
import com.sandeep03edu.passwordmanager.space


data class DisplayPageDisplayClass(
    val appModule: AppModule,
    val onPasswordItemClicked: (Password) -> Unit,
    val onCardItemClicked: (Card) -> Unit,
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { CredentialViewModel(appModule.credentialDataSource) }
        val state by viewModel.state.collectAsState()
        val onEvent = viewModel::onEvent

        println("$TAG Class State Cards: ${state.cards}")

        DisplayPageDisplay(
            state,
            onEvent,
            viewModel.newCard,
            viewModel.newPassword,
            viewModel,
            onPasswordItemClicked,
            onCardItemClicked
        )
    }

}

@Composable
fun DisplayPageDisplay(
    state: CredentialState,
    onEvent: (event: CredentialEvent) -> Unit,
    newCard: Card?,
    newPassword: Password?,
    viewModel: CredentialViewModel,
    onPasswordItemClicked: (Password) -> Unit,
    onCardItemClicked: (Card) -> Unit,
) {

    Scaffold(
        floatingActionButton = {
            if (!state.isAddNewCredentialSheetOpen) {
                FloatingActionButton(
                    onClick = {
                        onEvent(CredentialEvent.OnDisplayAddEditNewDataClick(Password(), Card()))
                    },
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        contentDescription = "Add contact",
                    )
                }
            }
        }
    ) {
        println("$TAG State Cards: ${state.cards}")
        println("$TAG State Password: ${state.passwords}")
        println("$TAG State Filter Password: ${state.filteredPasswords}")

        var selectedPasswordTag: String? by remember {
            mutableStateOf(null)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp)
        ) {

            item {

                if (state.cards.isNotEmpty()) {
                    Text(
                        text = "Your Cards",
                        style = TextStyle(
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 5.dp)
                    )
                }

                space(4)

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                ) {
                    items(state.cards) {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth(0.7f)
                        ) {
                            SecureHalfCardDisplay(it, onCardItemClicked = { card ->
                                onCardItemClicked(card)
                            })
                        }
                    }
                }
                space(4)
            }

            item {
                Text(
                    text = "Manage Passwords",
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 5.dp)
                )

                space(4)

                val passwordTags = getPasswordTagsWithIcons()
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f)
                ) {
                    items(passwordTags) {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth(0.3f)
                                .fillParentMaxHeight(1f)
                        ) {
                            PasswordTagCard(it, selectedPasswordTag) {
                                // onClick
                                if (selectedPasswordTag == null || selectedPasswordTag != it.first) {
                                    selectedPasswordTag = it.first
                                    onEvent(CredentialEvent.OnFilterChange(it.first))
                                } else {
                                    selectedPasswordTag = null
                                    onEvent(CredentialEvent.OnFilterChange("N/A"))
                                }
                            }
                        }
                    }
                }
                space(8)
            }

            item {
                val text: String
                if (selectedPasswordTag != null) {
                    text = "$selectedPasswordTag Passwords"

                } else {
                    text = "All Passwords"
                }

                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 5.dp)
                )

                space(8)
            }

            var list = state.passwords
            if (selectedPasswordTag != null && state.filteredPasswords != null) {
                list = state.filteredPasswords
            }



            items(list) {
                // Password -> it
                PasswordSecureHalfDisplay(password = it) {
                    // onClick
                    println("$TAG Clicked $it")
                    onPasswordItemClicked(it)
                }
            }

        }

        AddDataSheet(
            state = state,
            onEvent = onEvent,
            newCard = newCard,
            newPassword = newPassword
        )
    }

}
