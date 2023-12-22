package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.manager.authentication.data.getCredentialDeleteResponse
import com.sandeep03edu.passwordmanager.manager.authentication.data.getCredentialPostResponse
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.PasswordSecureHalfDisplay
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.PasswordTagCard
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.SecureHalfCardDisplay
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.utils.data.NetworkEndPoints
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserFirstName
import com.sandeep03edu.passwordmanager.manager.utils.data.getPasswordTagsWithIcons
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconLabeledTextField
import com.sandeep03edu.passwordmanager.space
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import kotlinx.coroutines.launch


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
            appModule,
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
    appModule: AppModule,
    state: CredentialState,
    onEvent: (event: CredentialEvent) -> Unit,
    newCard: Card?,
    newPassword: Password?,
    viewModel: CredentialViewModel,
    onPasswordItemClicked: (Password) -> Unit,
    onCardItemClicked: (Card) -> Unit,
) {
    val scope = rememberCoroutineScope()


    Scaffold(
        floatingActionButton = {
            if (!state.isAddNewCredentialSheetOpen) {
                FloatingActionButton(
                    onClick = {
                        onEvent(
                            CredentialEvent.OnDisplayAddEditNewDataClick(
                                Password(),
                                Card()
                            )
                        )
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


        var selectedCard: Card? by remember { mutableStateOf(null) }
        var selectedPassword: Password? by remember { mutableStateOf(null) }
        var isBottomSheetVisible by remember { mutableStateOf(selectedCard != null || selectedPassword != null) }

        if (isBottomSheetVisible) {
            FlexibleBottomSheet(
                onDismissRequest = {
                    selectedCard = null
                    selectedPassword = null
                    isBottomSheetVisible = false
                },
                sheetState = rememberFlexibleBottomSheetState(
                    isModal = true,
                    flexibleSheetSize = FlexibleSheetSize(
                        fullyExpanded = 0.3f,
                        intermediatelyExpanded = 0.3f,
                        slightlyExpanded = 0f
                    ),
                ),
            ) {
                var loadingLabel: String by remember { mutableStateOf("") }

                if (loadingLabel.isNotEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator()
                        space(4)
                        Text(text = loadingLabel)
                    }
                } else {
                    BottomSheetMenu(
                        appModule,
                        selectedCard,
                        selectedPassword,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                        onLoading = {
                            loadingLabel = it
                        }
                    )

                }
            }
        }


        var selectedPasswordTag: String? by remember {
            mutableStateOf(null)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome ${getLoggedInUserFirstName()}!",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(horizontal = 10.dp, vertical = 15.dp)
            )

            space(8)

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
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
                                SecureHalfCardDisplay(it,

                                    onCardItemClicked = { card ->
                                        onCardItemClicked(card)
                                    },
                                    onCardItemLongClicked = { card ->
                                        scope.launch {
                                            selectedCard = card
                                            selectedPassword = null
                                            isBottomSheetVisible = true
                                        }
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
                    PasswordSecureHalfDisplay(password = it,
                        onPasswordItemClicked = {
                            // onClick
                            println("$TAG Clicked $it")
                            onPasswordItemClicked(it)
                        },
                        onPasswordItemLongClicked = {
                            scope.launch {
                                selectedPassword = it
                                selectedCard = null
                                isBottomSheetVisible = true
                            }
                        }
                    )

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

@Composable
fun BottomSheetMenu(
    appModule: AppModule,
    card: Card?,
    password: Password?,
    modifier: Modifier = Modifier,
    onLoading: (String) -> Unit,
) {
    var selectedCard = card
    var selectedPassword = password
    val isCard: Boolean = selectedCard != null
    val isPass: Boolean = selectedPassword != null

    var syncStatus: Long by remember { mutableStateOf(-1) }
    if (isCard) {
        syncStatus = selectedCard!!.isSynced
    } else if (isPass) {
        syncStatus = selectedPassword!!.isSynced
    } else {
        return
    }

    println("$TAG SyncStatus:: $syncStatus || Card:: $selectedCard || Password:: $selectedPassword")
    var syncLabel: String= ""
    if (syncStatus == 0L) {
        // Not uploaded
        syncLabel = "Sync Now"
    } else if (syncStatus == 1L) {
        // Alr Uploaded
        syncLabel = "Un-Sync now"
    } else if (syncStatus == 2L) {
        // Upload on the way
        syncLabel = "Re-Sync"
    } else {
        return
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {

        IconLabeledTextField(
            leadingIcon = Icons.Default.Person,
            label = syncLabel,
            text = "",
            onClick = {
                // Perform action
                if (syncStatus == 0L || syncStatus == 2L) {
                    // Post/Re-Upload credential
                    onLoading("Syncing Credential to Server")
                    var url = ""
                    if (isCard) {
                        url = NetworkEndPoints.addUpdateCard
                    } else if (isPass) {
                        url = NetworkEndPoints.addUpdatePassword
                    }


                    println("$TAG Syncing URL: $url || Card: $selectedCard || Pass: $selectedPassword ")

                    if (url.isNotEmpty()) {
                        setSyncStatus(appModule, selectedCard, selectedPassword, 2L)

                        getCredentialPostResponse(
                            url = url,
                            card = selectedCard,
                            password = selectedPassword,
                            result = {
                                onLoading("")
                                println("$TAG Syncing Response:: $it")
                                if (it.success) {
                                    syncStatus = 1
                                    val cards = it.cards
                                    val passwords = it.passwords

                                    if (cards.size != 0) {
                                        selectedCard = cards[0]
                                    }
                                    if (passwords.size != 0) {
                                        selectedPassword = passwords[0]
                                    }

                                    setSyncStatus(
                                        appModule,
                                        selectedCard,
                                        selectedPassword,
                                        1L
                                    )
                                } else {
                                    syncStatus = 0
                                    // TODO : Toast Error
                                    setSyncStatus(
                                        appModule = appModule,
                                        selectedCard = selectedCard,
                                        selectedPassword = selectedPassword,
                                        0L
                                    )
                                }
                            })
                    }
                } else if (syncStatus == 1L) {
                    // Remove from server
                    onLoading("Un Syncing from server!!")

                    var url = ""
                    if (isCard) {
                        url = NetworkEndPoints.deleteCard
                    } else if (isPass) {
                        url = NetworkEndPoints.deletePassword
                    }
                    if (url.isNotEmpty()) {

                        setSyncStatus(
                            appModule = appModule,
                            selectedCard = selectedCard,
                            selectedPassword = selectedPassword,
                            2L
                        )

                        println("$TAG Un-Syncing URL: $url || Card: $selectedCard || Pass: $selectedPassword ")

                        getCredentialDeleteResponse(
                            url = url,
                            card = selectedCard,
                            password = selectedPassword,
                            result = {
                                onLoading("")
                                println("$TAG Un-Syncing Response:: $it")

                                if (it.success) {
                                    syncStatus = 1
                                    // Un Sync successful!!
                                    setSyncStatus(
                                        appModule = appModule,
                                        selectedCard = selectedCard,
                                        selectedPassword = selectedPassword,
                                        0L
                                    )
                                } else {
                                    syncStatus = 0
                                    // Un Sync failed!!
                                    // TODO : Toast error
                                    setSyncStatus(
                                        appModule = appModule,
                                        selectedCard = selectedCard,
                                        selectedPassword = selectedPassword,
                                        1L
                                    )
                                }
                            }
                        )
                    }
                }
            }
        )

        space(4)
        IconLabeledTextField(
            leadingIcon = Icons.Default.DeleteForever,
            label = "Delete",
            text = "",
            onClick = {
                // Perform action
            }
        )
    }

}

fun setSyncStatus(
    appModule: AppModule,
    selectedCard: Card?,
    selectedPassword: Password?,
    status: Long,
) {
    if (selectedCard != null) {
        selectedCard.isSynced = status
        appModule.credentialDataSource.addCard(selectedCard)
    } else if (selectedPassword != null) {
        selectedPassword.isSynced = status
        appModule.credentialDataSource.addPassword(selectedPassword)
    }
}
