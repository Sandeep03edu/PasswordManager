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
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.sandeep03edu.passwordmanager.ui.theme.getBackgroundColor
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

        DetailedCardDisplayPage(viewModel, state, onEvent, card)
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun DetailedCardDisplayPage(
    viewModel: CredentialViewModel,
    state: CredentialState,
    onEvent: (event: CredentialEvent) -> Unit,
    card: Card,
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
//                swipeableCardDisplay(
//                    card,
//                    numOfRows,
//                )
            } else {
                // Display one half of card
                detailedCardDisplay(
                    card,
                    numOfRows,
                    cardSize
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
fun detailedCardDisplay(card: Card, numOfRows: Int, cardSize: CardSize) {
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
//                        .weight(6f)
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
//                        .weight(6f)
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
    }
}

@Composable
fun swipeableCardDisplay(card: Card, cardWidth: Dp, noOfRows: Int, fontSize: TextUnit) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(1f)
    ) {
        item {

        }
    }
}

