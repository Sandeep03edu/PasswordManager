package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.CardSize
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.BottomHalfCardDisplay
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.UpperHalfCardDisplay
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.utils.presentation.BuildDesignedTitle
import com.sandeep03edu.passwordmanager.manager.utils.presentation.DisplaySnackbarToast
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconLabeledTextField
import com.sandeep03edu.passwordmanager.space
import com.sandeep03edu.passwordmanager.ui.theme.getBackgroundColor
import com.sandeep03edu.passwordmanager.ui.theme.getTextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first


data class DetailedCardDisplayPageClass(
    val appModule: AppModule,
    var card: Card,
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { CredentialViewModel(appModule.credentialDataSource) }
        val state by viewModel.state.collectAsState()
        val onEvent = viewModel::onEvent

        LaunchedEffect(state.isAddNewCredentialSheetOpen) {
            card = appModule.credentialDataSource.getCardById(card.appId).first()
        }

        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) {
            DetailedCardDisplayPage(
                viewModel,
                state,
                onEvent,
                card,
                coroutineScope,
                snackbarHostState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun DetailedCardDisplayPage(
    viewModel: CredentialViewModel,
    state: CredentialState,
    onEvent: (event: CredentialEvent) -> Unit,
    card: Card,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        floatingActionButton = {
            if (!state.isAddNewCredentialSheetOpen) {
                FloatingActionButton(
                    onClick = {
                        onEvent(CredentialEvent.OnDisplayAddEditNewDataClick(null, card))
                    },
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Edit Card",
                    )
                }
            }
        }
    ) {

        val windowSizeClass = calculateWindowSizeClass()

        Column(
            modifier = Modifier.fillMaxSize()
                .background(getBackgroundColor())
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            BuildDesignedTitle(
                list = mutableListOf("Ca", "rd"),
            )


            var numOfRows = 1
            var isSwipeable = true
            val cardSize = CardSize()

            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    numOfRows = 1
                    isSwipeable = true
                }

                WindowWidthSizeClass.Medium -> {
                    numOfRows = 2
                    isSwipeable = false
                    cardSize.cardWidth = 280.dp
                    cardSize.fontSize = 16.sp
                    cardSize.iconSize = 16.dp
                    cardSize.logoHeight = 30.dp
                    cardSize.headerFontSize = 20.sp
                }

                WindowWidthSizeClass.Expanded -> {
                    numOfRows = 3
                    isSwipeable = false
                    cardSize.cardWidth = 400.dp
                    cardSize.fontSize = 20.sp
                    cardSize.iconSize = 20.dp
                    cardSize.logoHeight = 40.dp
                    cardSize.headerFontSize = 28.sp
                }
            }

            if (isSwipeable) {
                // Display one half of card
                swipeableCardDisplay(
                    card,
                    numOfRows,
                    cardSize,
                    coroutineScope,
                    snackbarHostState
                )
            } else {
                // Display one half of card
                detailedCardDisplay(
                    card,
                    numOfRows,
                    cardSize,
                    coroutineScope,
                    snackbarHostState
                )
            }

        }
    }

    AddDataSheet(
        state = state,
        onEvent = onEvent,
        newCard = viewModel.newCard,
        newPassword = viewModel.newPassword
    )

}

@Composable
fun detailedCardDisplay(
    card: Card,
    numOfRows: Int,
    cardSize: CardSize,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    LazyVerticalGrid(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(1f),
        columns = GridCells.Fixed(numOfRows)
    ) {
        item(span = { GridItemSpan(numOfRows) }) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(cardSize.cardWidth)
                        .aspectRatio(1.75f)
                        .wrapContentSize()
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                ) {
                    UpperHalfCardDisplay(
                        card = card,
                        cardSize = cardSize
                    )
                }


                Box(
                    modifier = Modifier
                        .width(cardSize.cardWidth)
                        .aspectRatio(1.75f)
                        .wrapContentSize()
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                ) {
                    BottomHalfCardDisplay(
                        card = card,
                        cardSize = cardSize
                    )
                }

            }
        }

        item(span = { GridItemSpan(numOfRows) }) { space(8) }

        item(span = { GridItemSpan(numOfRows) }) {
            Text(
                text = "Customer Details",
                fontSize = (((cardSize.fontSize).value + 4).sp),
                color = getTextColor(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }

        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Person,
                text = card.cardHolderName,
                label = "Customer Name",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Person,
                text = card.issuerName,
                label = "Issuer Name",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Person,
                text = card.cardNumber,
                label = "Card Number",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Person,
                text = card.cardType,
                label = "Card Type",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }

        item(span = { GridItemSpan(numOfRows) }) { space(8) }

        item(span = { GridItemSpan(numOfRows) }) {
            Text(
                text = "Issue and Expiry Dates",
                fontSize = (((cardSize.fontSize).value + 4).sp),
                color = getTextColor(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }

        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.CalendarMonth,
                text = card.issueDate,
                label = "Issue Date",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }

            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.CalendarMonth,
                text = card.expiryDate,
                label = "Expiry Date",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }

        item(span = { GridItemSpan(numOfRows) }) { space(8) }

        item(span = { GridItemSpan(numOfRows) }) {
            Text(
                text = "Security Keys",
                fontSize = (((cardSize.fontSize).value + 4).sp),
                color = getTextColor(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }


        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Password,
                text = card.cvv,
                label = "CVV",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Pin,
                text = card.pin,
                label = "Pin",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
    }
}



@Composable
fun swipeableCardDisplay(
    card: Card,
    numOfRows: Int,
    cardSize: CardSize,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    LazyVerticalGrid(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(1f),
        columns = GridCells.Fixed(numOfRows)
    ) {
        item(span = { GridItemSpan(numOfRows) }) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(cardSize.cardWidth)
                        .aspectRatio(1.75f)
                        .wrapContentSize()
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                ) {
                    UpperHalfCardDisplay(
                        card = card,
                        cardSize = cardSize
                    )
                }


//                Box(
//                    modifier = Modifier
//                        .width(cardSize.cardWidth)
//                        .aspectRatio(1.75f)
//                        .wrapContentSize()
//                        .padding(vertical = 5.dp, horizontal = 10.dp)
//                ) {
//                    BottomHalfCardDisplay(
//                        card = card,
//                        cardSize = cardSize
//                    )
//                }

            }
        }

        item(span = { GridItemSpan(numOfRows) }) { space(8) }

        item(span = { GridItemSpan(numOfRows) }) {
            Text(
                text = "Customer Details",
                fontSize = (((cardSize.fontSize).value + 4).sp),
                color = getTextColor(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }

        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Person,
                text = card.cardHolderName,
                label = "Customer Name",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Person,
                text = card.issuerName,
                label = "Issuer Name",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Person,
                text = card.cardNumber,
                label = "Card Number",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Person,
                text = card.cardType,
                label = "Card Type",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }

        item(span = { GridItemSpan(numOfRows) }) { space(8) }

        item(span = { GridItemSpan(numOfRows) }) {
            Text(
                text = "Issue and Expiry Dates",
                fontSize = (((cardSize.fontSize).value + 4).sp),
                color = getTextColor(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }

        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.CalendarMonth,
                text = card.issueDate,
                label = "Issue Date",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }

            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.CalendarMonth,
                text = card.expiryDate,
                label = "Expiry Date",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }

        item(span = { GridItemSpan(numOfRows) }) { space(8) }

        item(span = { GridItemSpan(numOfRows) }) {
            Text(
                text = "Security Keys",
                fontSize = (((cardSize.fontSize).value + 4).sp),
                color = getTextColor(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }


        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Password,
                text = card.cvv,
                label = "CVV",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
        item {
            IconLabeledTextField(
                leadingIcon = Icons.Default.Pin,
                text = card.pin,
                label = "Pin",
                trailingIcon = Icons.Default.ContentCopy,
                onClick = {
                    onCopyClick(
                        text = card.cardHolderName,
                        clipboardManager = clipboardManager,
                        coroutineScope = coroutineScope,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }
    }
}

fun onCopyClick(
    text: String,
    clipboardManager: ClipboardManager,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    clipboardManager.setText(AnnotatedString(text))

    DisplaySnackbarToast(
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope,
        message = "Copied to clipboard!!"
    )
}