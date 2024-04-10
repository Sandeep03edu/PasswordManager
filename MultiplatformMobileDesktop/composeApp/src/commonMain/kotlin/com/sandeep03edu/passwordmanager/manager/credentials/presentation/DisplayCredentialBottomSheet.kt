package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sandeep03edu.passwordmanager.manager.authentication.data.getCredentialDeleteResponse
import com.sandeep03edu.passwordmanager.manager.authentication.data.getCredentialPostResponse
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.utils.data.NetworkEndPoints
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconLabeledTextField
import com.sandeep03edu.passwordmanager.space

@Composable
fun BottomSheetMenu(
    appModule: AppModule,
    card: Card?,
    password: Password?,
    modifier: Modifier = Modifier,
    onLoading: (String) -> Unit,
    onComplete: () -> Unit,
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
    var syncLabel: String = ""
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

                                    onComplete()
                                } else {
                                    syncStatus = 0
                                    // TODO : Toast Error
                                    setSyncStatus(
                                        appModule = appModule,
                                        selectedCard = selectedCard,
                                        selectedPassword = selectedPassword,
                                        0L
                                    )

                                    onComplete()
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
                                    onComplete()
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
                                    onComplete()
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
                // Perform delete action
                if (syncStatus == 0L) {
                    // Not uploaded to server!!
                    onLoading("Deleting credentials locally")

                    if (selectedCard != null) {
                        appModule.credentialDataSource.deleteCardById(selectedCard!!.appId)
                    } else if (selectedPassword != null) {
                        appModule.credentialDataSource.deletePasswordById(selectedPassword!!.appId)
                    }

                    onLoading("")

                    onComplete()
                } else {
                    // Already uploaded to server
                    onLoading("Deleting credentials from server")

                    var url = ""
                    if (selectedCard != null) {
                        url = NetworkEndPoints.deleteCard
                    } else if (selectedPassword != null) {
                        url = NetworkEndPoints.deletePassword
                    }

                    getCredentialDeleteResponse(
                        url = url,
                        card = selectedCard,
                        password = selectedPassword,
                        result = {
                            if (it.success) {
                                // Deleted from server successful
                                onLoading("Deleting credentials locally")

                                if (selectedCard != null) {
                                    appModule.credentialDataSource.deleteCardById(selectedCard!!.appId)
                                } else if (selectedPassword != null) {
                                    appModule.credentialDataSource.deletePasswordById(
                                        selectedPassword!!.appId
                                    )
                                }

                                onLoading("")

                                onComplete()
                            } else {
                                // TODO : Toast error message
                                onComplete()
                            }
                        }
                    )
                }
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
