package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.BottomHalfCardDisplay
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.UpperHalfCardDisplay
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.utils.data.getRandomDarkCardBackground
import com.sandeep03edu.passwordmanager.manager.utils.presentation.BuildDesignedHeader
import com.sandeep03edu.passwordmanager.manager.utils.presentation.BuildDesignedTitle
import com.sandeep03edu.passwordmanager.space
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
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
//            Text(
//                buildAnnotatedString
//                {
//                    withStyle(
//                        style = SpanStyle(
//                            fontWeight = FontWeight.ExtraBold,
//                            color = MaterialTheme.colorScheme.primary,
//                            fontSize = 40.sp
//                        )
//                    ) {
//                        append("Ca")
//                    }
//                    withStyle(
//                        style = SpanStyle(
//                            fontSize = 36.sp,
//                            fontWeight = FontWeight.Bold,
//                        )
//                    ) {
//                        append("rd")
//                    }
//                }
//            )

            BuildDesignedTitle(
                list = mutableListOf("Ca", "rd"),
            )

/*
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    CompactColumnView(card)
                }

                WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
                    MediumExpandedRowView(card)
                }
            }
*/

            when(windowSizeClass.heightSizeClass){
                WindowHeightSizeClass.Compact->{
                    MediumExpandedRowView(card)
                }
                WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded->{
                    CompactColumnView(card)
                }
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
private fun CompactColumnView(card: Card) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(1f)
    ) {
        item {
            val cardBackground = remember { getRandomDarkCardBackground() }


            Box(modifier = Modifier.fillMaxWidth()) {
                UpperHalfCardDisplay(
                    card,
                    cardBackground
                )
            }

            space(8)

            Box(modifier = Modifier.fillMaxWidth()) {
                BottomHalfCardDisplay(
                    card,
                    cardBackground
                )
            }
            space(16)
        }
    }
}

@Composable
private fun MediumExpandedRowView(card: Card) {
    LazyRow(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(1f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            val cardBackground = remember { getRandomDarkCardBackground() }

            Box(modifier = Modifier.fillMaxWidth()) {
                UpperHalfCardDisplay(
                    card,
                    cardBackground
                )
            }

            space(0, 8)

            Box(modifier = Modifier.fillMaxWidth()) {
                BottomHalfCardDisplay(
                    card,
                    cardBackground
                )
            }

        }

    }
}