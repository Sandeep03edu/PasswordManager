package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.BottomSheetDemo
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.TagCard
import com.sandeep03edu.passwordmanager.manager.utils.data.getCardIssuerLogoList
import com.sandeep03edu.passwordmanager.manager.utils.data.getCardIssuerNameList
import com.sandeep03edu.passwordmanager.manager.utils.data.getCardTypesList
import com.sandeep03edu.passwordmanager.manager.utils.data.getPasswordTags
import com.sandeep03edu.passwordmanager.manager.utils.presentation.BuildDesignedTitle
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CardButton
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CircularImage
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconDropDownField
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconEditNumberField
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconEditTextField
import com.sandeep03edu.passwordmanager.manager.utils.presentation.MonthPicker
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AddDataSheet(
    state: CredentialState,
    onEvent: (event: CredentialEvent) -> Unit,
    newCard: Card?,
    newPassword: Password?,
) {
    BottomSheetDemo(
        // TODO : Update this after implementation
        visible = state.isAddNewCredentialSheetOpen,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            var isCardUpdate: Boolean? by remember { mutableStateOf(null) }
            val dropDownItems = listOf("Card", "Password")
            var selectedDropDownItem by remember { mutableStateOf("") }

            val windowSizeClass = calculateWindowSizeClass()

/*
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    DisplayCompactColumnView(
                        onEvent,
                        newCard,
                        newPassword,
                        selectedDropDownItem,
                        dropDownItems,
                        isCardUpdate,
                        state,
                        onSelectedDropDownItemChange = {
                            selectedDropDownItem = it
                        },
                        onCardUpdateChange = {
                            isCardUpdate = it
                        }
                    )
                }

                WindowWidthSizeClass.Medium , WindowWidthSizeClass.Expanded-> {
                    DisplayMediumGridView(
                        onEvent,
                        newCard,
                        newPassword,
                        selectedDropDownItem,
                        dropDownItems,
                        isCardUpdate,
                        state,
                        onSelectedDropDownItemChange = {
                            selectedDropDownItem = it
                        },
                        onCardUpdateChange = {
                            isCardUpdate = it
                        }
                    )
                }
            }
*/

            when(windowSizeClass.heightSizeClass){
                WindowHeightSizeClass.Compact->{
                    DisplayMediumGridView(
                        onEvent,
                        newCard,
                        newPassword,
                        selectedDropDownItem,
                        dropDownItems,
                        isCardUpdate,
                        state,
                        onSelectedDropDownItemChange = {
                            selectedDropDownItem = it
                        },
                        onCardUpdateChange = {
                            isCardUpdate = it
                        }
                    )
                }
                WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded->{
                    DisplayCompactColumnView(
                        onEvent,
                        newCard,
                        newPassword,
                        selectedDropDownItem,
                        dropDownItems,
                        isCardUpdate,
                        state,
                        onSelectedDropDownItemChange = {
                            selectedDropDownItem = it
                        },
                        onCardUpdateChange = {
                            isCardUpdate = it
                        }
                    )
                }
            }

        }
    }
}

@Composable
private fun DisplayCompactColumnView(
    onEvent: (event: CredentialEvent) -> Unit,
    newCard: Card?,
    newPassword: Password?,
    selectedDropDownItem: String,
    dropDownItems: List<String>,
    isCardUpdate: Boolean?,
    state: CredentialState,
    onSelectedDropDownItemChange: (String) -> Unit,
    onCardUpdateChange: (Boolean) -> Unit,
) {
    val headerList: MutableList<String> =
        if ((newCard == null && newPassword == null) || (newCard != null && newPassword != null)) {
            mutableListOf("A", "dd ", "C", "redentials")
        } else {
            mutableListOf("E", "dit ", "C", "redentials")
        }
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            AddCredentialHeader(
                onEvent,
                headerList = headerList
            )
        }

        item {
            // TODO : Change with app logo
            AddCredentialLogo()
        }

        item {
            if ((newCard == null && newPassword == null) || (newCard != null && newPassword != null)) {
                // Add new data will display drop down
                IconDropDownField(
                    imageVector = Icons.Rounded.Person,
                    label = "Select Type",
                    text = selectedDropDownItem,
                    dropDownItems = dropDownItems,
                    onSelectedItem = {
                        onSelectedDropDownItemChange(it)
                        onCardUpdateChange(it == "Card")
                    },
                )
            } else {
                // Edit data will display drop down
                onCardUpdateChange(newCard != null)
            }
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                if (isCardUpdate == true) {
                    DisplayAddCardForm(newCard, onEvent, state)
                } else if (isCardUpdate == false) {
                    DisplayAddPasswordForm(newPassword, onEvent, state)
                }
            }
            space(16)
        }

        item {
            SubmitButton(isCardUpdate, onEvent)
        }
    }
}

@Composable
private fun DisplayMediumGridView(
    onEvent: (event: CredentialEvent) -> Unit,
    newCard: Card?,
    newPassword: Password?,
    selectedDropDownItem: String,
    dropDownItems: List<String>,
    isCardUpdate: Boolean?,
    state: CredentialState,
    onSelectedDropDownItemChange: (String) -> Unit,
    onCardUpdateChange: (Boolean) -> Unit,
) {
    val headerList: MutableList<String> =
        if ((newCard == null && newPassword == null) || (newCard != null && newPassword != null)) {
            mutableListOf("A", "dd ", "C", "redentials")
        } else {
            mutableListOf("E", "dit ", "C", "redentials")
        }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AddCredentialHeader(
                        onEvent,
                        headerList = headerList
                    )

                    // TODO : Change with app logo
                    AddCredentialLogo()
                }
            }
        }


        LazyColumn(
            modifier = Modifier.weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if ((newCard == null && newPassword == null) || (newCard != null && newPassword != null)) {
                        // Add new data will display drop down
                        IconDropDownField(
                            imageVector = Icons.Rounded.Person,
                            label = "Select Type",
                            text = selectedDropDownItem,
                            dropDownItems = dropDownItems,
                            onSelectedItem = {
                                onSelectedDropDownItemChange(it)
                                onCardUpdateChange(it == "Card")
                            },
                        )
                        space(8)
                    } else {
                        // Edit data will display drop down
                        onCardUpdateChange(newCard != null)
                    }


                    if (isCardUpdate == true) {
                        DisplayAddCardForm(newCard, onEvent, state)
                    } else if (isCardUpdate == false) {
                        DisplayAddPasswordForm(newPassword, onEvent, state)
                    }

                    space(16)

                    SubmitButton(isCardUpdate, onEvent)
                }
            }
        }
    }
}

@Composable
private fun SubmitButton(
    isCardUpdate: Boolean?,
    onEvent: (event: CredentialEvent) -> Unit,
) {
    CardButton(
        backgroundColor = MaterialTheme.colorScheme.secondary,
        text = "Submit",
        onClick = {
            if (isCardUpdate == true) {
                onEvent(CredentialEvent.SaveCard)
            } else if (isCardUpdate == false) {
                onEvent(CredentialEvent.SavePassword)
            }
        },
    )
}

@Composable
private fun AddCredentialLogo() {
    CircularImage(
        painter = paintResource(SharedRes.images.avatar),
        modifier = Modifier.size(100.dp),
    )

    space(16)
}

@Composable
private fun AddCredentialHeader(
    onEvent: (event: CredentialEvent) -> Unit,
    headerList: MutableList<String>,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onEvent(CredentialEvent.OnDismissAddEditNewDataClick)
                }
        )

        space(width = 16)

        BuildDesignedTitle(headerList)
    }

    space(32)
}

@Composable
fun DisplayAddCardForm(
    newCard: Card?,
    onEvent: (event: CredentialEvent) -> Unit,
    state: CredentialState,
) {


    space(16)

    Text(
        text = "Customer Details",
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 4.dp)
    )

    space(4)

    IconEditTextField(
        leadingIcon = Icons.Rounded.Person,
        label = "Customer Name",
        text = newCard?.cardHolderName ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnCardHolderNameChanged(it))
        },
        errorMessage = state.cardHolderNameError
    )

    space(4)

    IconDropDownField(
        imageVector = Icons.Rounded.Person,
        label = "Issuer Name",
        text = newCard?.issuerName ?: "",
        dropDownItems = getCardIssuerNameList(),
        dropDownItemIcons = getCardIssuerLogoList(),
        onSelectedItem = {
            onEvent(CredentialEvent.OnCardIssuerNameChanged(it))
        },
        errorMessage = state.cardIssuerNameError
    )


    space(4)

    IconEditTextField(
        leadingIcon = Icons.Rounded.Person,
        label = "Card number",
        text = newCard?.cardNumber ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnCardNumberChanged(it))
        },
        errorMessage = state.cardNumberError
    )

    space(4)

    IconDropDownField(
        imageVector = Icons.Rounded.Person,
        label = "Card type",
        text = newCard?.cardType ?: "",
        dropDownItems = getCardTypesList(),
        onSelectedItem = {
            onEvent(CredentialEvent.OnCardTypeChanged(it))
        },
        errorMessage = state.cardTypeError
    )


    space(16)

    Text(
        text = "Issue and Expiry Date",
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 4.dp)

    )

    space(4)

    var selectedMonthDialog: String? by remember { mutableStateOf(null) }

    IconEditTextField(
        leadingIcon = Icons.Rounded.Person,
        label = "Issue Date",
        text = newCard?.issueDate ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnCardIssueDateChanged(it))
        },
        errorMessage = if (state.cardDateError == null) null else "",
        onClick = {
            println("$TAG AddCredentials Clicked Issue Date $selectedMonthDialog")
            selectedMonthDialog = "IssueDate"
        },
        enabled = false
    )

    space(4)

    IconEditTextField(
        leadingIcon = Icons.Rounded.Person,
        label = "Expiry Date",
        text = newCard?.expiryDate ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnCardExpiryDateChanged(it))
        },
        errorMessage = state.cardDateError,
        onClick = {
            selectedMonthDialog = "ExpiryDate"
        },
        enabled = false
    )

    space(16)

    Text(
        text = "Security Keys",
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 4.dp)
    )

    space(4)

    IconEditNumberField(
        leadingIcon = Icons.Rounded.Person,
        label = "Pin",
        text = newCard?.pin ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnCardPinChanged(it))
        },
        errorMessage = if (state.cardSecurityKeyError == null) null else "",
        maxLength = 4
    )

    space(4)

    IconEditNumberField(
        leadingIcon = Icons.Rounded.Person,
        label = "Cvv",
        text = newCard?.cvv ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnCardCvvChanged(it))
        },
        errorMessage = state.cardSecurityKeyError
    )


    // Displaying Month picker if it is open
    MonthPicker(
        visible = selectedMonthDialog != null,
        currentMonth = 1,
        currentYear = 2023,
        confirmButtonCLicked = { month_, year_ ->
            val dateStr = "$month_/$year_"
            if (selectedMonthDialog == "IssueDate") {
                onEvent(CredentialEvent.OnCardIssueDateChanged(dateStr))
            } else if (selectedMonthDialog == "ExpiryDate") {
                onEvent(CredentialEvent.OnCardExpiryDateChanged(dateStr))
            }
            selectedMonthDialog = null
            println("$TAG Selected Month - $month_ & $year_")
        },
        cancelClicked = {
            selectedMonthDialog = null
        }
    )


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DisplayAddPasswordForm(
    newPassword: Password?,
    onEvent: (event: CredentialEvent) -> Unit,
    state: CredentialState,
) {
    space(16)

    Text(
        text = "Password Details",
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 4.dp)
    )

    space(4)

    IconEditTextField(
        leadingIcon = Icons.Rounded.Person,
        label = "Title",
        text = newPassword?.title ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnPasswordTitleChange(it))
        },
        errorMessage = state.passwordTitleError
    )

    space(4)

    IconEditTextField(
        leadingIcon = Icons.Rounded.Person,
        label = "Url",
        text = newPassword?.url ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnPasswordUrlChange(it))
        },
        errorMessage = state.passwordUrlError,
        prefix = "https://",
    )

    space(16)

    Text(
        text = "User Details",
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 4.dp)
    )

    space(4)

    IconEditTextField(
        leadingIcon = Icons.Rounded.Person,
        label = "Username",
        text = newPassword?.username ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnPasswordUsernameChange(it))
        },
        errorMessage = if (state.passwordUserDetailError == null) null else ""
    )

    space(4)

    IconEditTextField(
        leadingIcon = Icons.Rounded.Person,
        label = "EmailId",
        text = newPassword?.email ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnPasswordEmailIdChange(it))
        },
        errorMessage = state.passwordUserDetailError
    )

    space(16)

    Text(
        text = "Security Keys",
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 4.dp)
    )

    space(4)

    IconEditTextField(
        leadingIcon = Icons.Rounded.Person,
        label = "Password",
        text = newPassword?.password ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnPasswordPasswordChange(it))
        },
        errorMessage = if (state.passwordSecurityKeyError == null) null else ""
    )

    space(4)

    IconEditNumberField(
        leadingIcon = Icons.Rounded.Person,
        label = "Pin",
        text = newPassword?.pin ?: "",
        onTextChange = {
            onEvent(CredentialEvent.OnPasswordPinChange(it))
        },
        errorMessage = state.passwordSecurityKeyError
    )

    space(16)

    Text(
        text = "Tags",
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 4.dp)
    )

    space(4)

    val tagLists = getPasswordTags()
    val selectedTags = remember { mutableStateListOf<String>() }



    FlowRow(
    ) {
        tagLists.forEach {
            TagCard(it, selectedTags.contains(it)) {
                onEvent(CredentialEvent.OnPasswordTagChange(it))
                if (selectedTags.contains(it)) {
                    selectedTags.remove(it)
                } else {
                    selectedTags.add(it)
                }
            }
        }
    }

    space(4)

    if (state.passwordTagsError != null) {
        Text(
            text = state.passwordTagsError,
            style = TextStyle(
                color = MaterialTheme.colorScheme.error,
                background = MaterialTheme.colorScheme.onError
            ),
            modifier = Modifier.padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 4.dp)
        )
    }


}