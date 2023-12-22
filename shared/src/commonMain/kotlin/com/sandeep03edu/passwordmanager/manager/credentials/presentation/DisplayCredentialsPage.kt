package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserFirstName
import com.sandeep03edu.passwordmanager.manager.utils.data.getPasswordTagsWithIcons
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
                        },
                        onComplete = {
                            isBottomSheetVisible = false
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

                    Text(
                        text = "Your Cards",
                        style = TextStyle(
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 5.dp)
                    )

                    space(4)

                    if (state.cards.isNotEmpty()) {
                        // Display all cards
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
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
                    } else {
                        // Display Add card option
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .aspectRatio(1.75f)
                                    .padding(5.dp)
                                    .dashedBorder(2.dp, Color.Red, 8.dp),
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                        .clickable {
                                            onEvent(
                                                CredentialEvent.OnDisplayAddEditNewDataClick(
                                                    null,
                                                    Card()
                                                )
                                            )
                                        }
                                ) {
                                    Image(
                                        imageVector = Icons.Default.AddCircle,
                                        contentDescription = null
                                    )

                                    space(8)

                                    Text(
                                        text = "Add your card here!!",
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }
                    }

                    space(16)
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

                if (state.passwords.isNotEmpty()) {
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
                } else {
                    item {
                        // Display Add new password option
                        Card(
                            modifier = Modifier.fillMaxWidth()
                                .aspectRatio(5.5f)
                                .background(MaterialTheme.colorScheme.background)
                                .padding(8.dp)
                                .dashedBorder(2.dp, Color.Red, 8.dp)
                                .clickable {
                                    onEvent(
                                        CredentialEvent.OnDisplayAddEditNewDataClick(
                                            Password(),
                                            null
                                        )
                                    )
                                },
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null
                                )

                                space(width = 8)

                                Text(
                                    text = "Add your Password here!!",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }
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

fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadiusDp: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val cornerRadiusPx = density.run { cornerRadiusDp.toPx() }

        this.then(
            Modifier.drawWithCache {
                onDrawBehind {
                    val stroke = Stroke(
                        width = strokeWidthPx,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )

                    drawRoundRect(
                        color = color,
                        style = stroke,
                        cornerRadius = CornerRadius(cornerRadiusPx)
                    )
                }
            }
        )
    }
)
