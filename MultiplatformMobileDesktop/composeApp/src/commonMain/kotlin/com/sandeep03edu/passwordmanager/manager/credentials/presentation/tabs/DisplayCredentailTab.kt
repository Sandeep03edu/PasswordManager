package com.sandeep03edu.passwordmanager.manager.credentials.presentation.tabs

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.AddDataSheet
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.BottomSheetMenu
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.CredentialEvent
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.CredentialState
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.CredentialViewModel
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.TAG
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.PasswordSecureHalfDisplay
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.PasswordTagCard
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.components.SecureHalfCardDisplay
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserName
import com.sandeep03edu.passwordmanager.manager.utils.data.getPasswordTagsWithIcons
import com.sandeep03edu.passwordmanager.manager.utils.presentation.bottomDialogModifier
import com.sandeep03edu.passwordmanager.space
import com.sandeep03edu.passwordmanager.ui.theme.getBackgroundColor
import com.sandeep03edu.passwordmanager.ui.theme.getTextColor
import com.sandeep03edu.passwordmanager.ui.theme.getTextColorInverse
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

var cardWidth = 280.dp

class DisplayCredentialTab(
    val appModule: AppModule,
    val onPasswordItemClicked: (Password) -> Unit,
    val onCardItemClicked: (Card) -> Unit,
) : Tab {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {

        val windowSizeClass = calculateWindowSizeClass()
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                cardWidth = 280.dp
            }

            WindowWidthSizeClass.Medium -> {
                cardWidth = 300.dp
            }

            WindowWidthSizeClass.Expanded -> {
                cardWidth = 320.dp
            }
        }


        val viewModel = rememberScreenModel { CredentialViewModel(appModule.credentialDataSource) }
        val state by viewModel.state.collectAsState()
        val onEvent = viewModel::onEvent

        println("$TAG Class State Cards: ${state.cards}")
        println("$TAG Class State Passwords: ${state.passwords}")

        var selectedCard: Card? by remember { mutableStateOf(null) }
        var selectedPassword: Password? by remember { mutableStateOf(null) }
        var isBottomSheetVisible by remember { mutableStateOf(selectedCard != null || selectedPassword != null) }
        var selectedPasswordTag: String? by rememberSaveable {
            mutableStateOf(null)
        }

        DisplayPageDisplay(
            appModule,
            state,
            onEvent,
            viewModel.newCard,
            viewModel.newPassword,
            viewModel,
            onPasswordItemClicked,
            onCardItemClicked,
            selectedCard,
            selectedPassword,
            isBottomSheetVisible,
            selectedPasswordTag,
            onSelectedCardChange = {
                selectedCard = it
            },
            onSelectedPasswordChange = {
                selectedPassword = it
            },
            onBottomSheetVisibleChange = {
                isBottomSheetVisible = it
            },
            onSelectedPasswordTagChanged = {
                selectedPasswordTag = it
            }
        )
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Person)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon
                )
            }
        }

}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
    selectedCard: Card?,
    selectedPassword: Password?,
    isBottomSheetVisible: Boolean,
    selectedPasswordTag: String?,
    onSelectedCardChange: (Card?) -> Unit,
    onSelectedPasswordChange: (Password?) -> Unit,
    onBottomSheetVisibleChange: (Boolean) -> Unit,
    onSelectedPasswordTagChanged: (String?) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier
                    .bottomDialogModifier()
                    .background(Color.Red)
            )
        },
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


        if (isBottomSheetVisible) {
            FlexibleBottomSheet(
                onDismissRequest = {
                    onSelectedCardChange(null)
                    onSelectedPasswordChange(null)
                    onBottomSheetVisibleChange(false)
                },
                sheetState = rememberFlexibleBottomSheetState(
                    isModal = true,
                    flexibleSheetSize = FlexibleSheetSize(
                        fullyExpanded = 0.8f,
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
                            onBottomSheetVisibleChange(false)
                        }
                    )

                }
            }
        }

        val windowSizeClass = calculateWindowSizeClass()

        Column(
            modifier = Modifier.fillMaxSize()
                .background(getBackgroundColor()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {


/*
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    DisplayCompactColumnView(
                        state,
                        scope,
                        onCardItemClicked,
                        onSelectedCardChange,
                        onSelectedPasswordChange,
                        onBottomSheetVisibleChange,
                        onEvent,
                        selectedPasswordTag,
                        onSelectedPasswordTagChanged,
                        onPasswordItemClicked
                    )
                }

                WindowWidthSizeClass.Medium -> {
                    DisplayMediumColumnView(
                        state,
                        scope,
                        onCardItemClicked,
                        onSelectedCardChange,
                        onSelectedPasswordChange,
                        onBottomSheetVisibleChange,
                        onEvent,
                        selectedPasswordTag,
                        onSelectedPasswordTagChanged,
                        onPasswordItemClicked
                    )
                }

                WindowWidthSizeClass.Expanded -> {
                    DisplayExpandedColumnView(
                        state,
                        scope,
                        onCardItemClicked,
                        onSelectedCardChange,
                        onSelectedPasswordChange,
                        onBottomSheetVisibleChange,
                        onEvent,
                        selectedPasswordTag,
                        onSelectedPasswordTagChanged,
                        onPasswordItemClicked
                    )
                }
            }
*/
            when (windowSizeClass.heightSizeClass) {
                WindowHeightSizeClass.Compact -> {
                    DisplayMediumColumnView(
                        state,
                        scope,
                        onCardItemClicked,
                        onSelectedCardChange,
                        onSelectedPasswordChange,
                        onBottomSheetVisibleChange,
                        onEvent,
                        selectedPasswordTag,
                        onSelectedPasswordTagChanged,
                        onPasswordItemClicked
                    )
                }

                WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> {
                    DisplayCompactColumnView(
                        state,
                        scope,
                        onCardItemClicked,
                        onSelectedCardChange,
                        onSelectedPasswordChange,
                        onBottomSheetVisibleChange,
                        onEvent,
                        selectedPasswordTag,
                        onSelectedPasswordTagChanged,
                        onPasswordItemClicked
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
private fun DisplayCompactColumnView(
    state: CredentialState,
    scope: CoroutineScope,
    onCardItemClicked: (Card) -> Unit,
    onSelectedCardChange: (Card?) -> Unit,
    onSelectedPasswordChange: (Password?) -> Unit,
    onBottomSheetVisibleChange: (Boolean) -> Unit,
    onEvent: (event: CredentialEvent) -> Unit,
    selectedPasswordTag: String?,
    onSelectedPasswordTagChanged: (String?) -> Unit,
    onPasswordItemClicked: (Password) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        item {
            Header()
        }
        item {
            CardHeader()
        }

        item {
            if (state.cards.isNotEmpty()) {
                // Display all cards
                DisplayRowCards(
                    state,
                    scope,
                    onCardItemClicked,
                    onSelectedCardChange,
                    onSelectedPasswordChange,
                    onBottomSheetVisibleChange,
                )
            } else {
                // Display Add card option
                AddNewCard(onEvent)
            }
            space(16)
        }


        item {
            ManagePasswordHeader()
        }

        item {
            DisplayRowPasswordTags(
                selectedPasswordTag,
                onEvent,
                onSelectedPasswordTagChanged
            )
        }

        item {
            FilterPasswordDisplayHeader(selectedPasswordTag)
        }

        var list = state.passwords
        if (selectedPasswordTag != null && state.filteredPasswords != null) {
            list = state.filteredPasswords
        }

        if (state.passwords.isNotEmpty()) {
            items(list) {
                // Password -> it
                PasswordItemDisplay(
                    it,
                    onPasswordItemClicked,
                    scope,
                    onSelectedPasswordChange,
                    onSelectedCardChange,
                    onBottomSheetVisibleChange
                )
            }
        } else {
            item {
                // Display Add new password option
                AddNewPasswordDisplay(onEvent)
            }
        }
    }
}

@Composable
private fun DisplayMediumColumnView(
    state: CredentialState,
    scope: CoroutineScope,
    onCardItemClicked: (Card) -> Unit,
    onSelectedCardChange: (Card?) -> Unit,
    onSelectedPasswordChange: (Password?) -> Unit,
    onBottomSheetVisibleChange: (Boolean) -> Unit,
    onEvent: (event: CredentialEvent) -> Unit,
    selectedPasswordTag: String?,
    onSelectedPasswordTagChanged: (String?) -> Unit,
    onPasswordItemClicked: (Password) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Header()
        Row(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                item {
                    CardHeader()
                }

                if (state.cards.isNotEmpty()) {
                    // Display all cards
                    items(state.cards) {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth(1f)
                        ) {
                            SecureHalfCardDisplay(it,
                                onCardItemClicked = { card ->
                                    onCardItemClicked(card)
                                },
                                onCardItemLongClicked = { card ->
                                    scope.launch {
                                        onSelectedCardChange(card)
                                        onSelectedPasswordChange(null)
                                        onBottomSheetVisibleChange(true)
                                    }
                                })
                        }
                    }

                } else {
                    // Display Add card option
                    item {
                        AddNewCard(onEvent)
                    }
                }

                item {
                    space(16)
                }

            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    ManagePasswordHeader()
                }

                item {
                    DisplayRowPasswordTags(
                        selectedPasswordTag,
                        onEvent,
                        onSelectedPasswordTagChanged
                    )
                }

                item {
                    FilterPasswordDisplayHeader(selectedPasswordTag)
                }

                var list = state.passwords
                if (selectedPasswordTag != null && state.filteredPasswords != null) {
                    list = state.filteredPasswords
                }
                if (state.passwords.isNotEmpty()) {
                    items(list) {
                        // Password -> it
                        PasswordItemDisplay(
                            it,
                            onPasswordItemClicked,
                            scope,
                            onSelectedPasswordChange,
                            onSelectedCardChange,
                            onBottomSheetVisibleChange
                        )
                    }
                } else {
                    item {
                        // Display Add new password option
                        AddNewPasswordDisplay(onEvent)
                    }
                }


            }
        }
    }
}

@Composable
private fun DisplayExpandedColumnView(
    state: CredentialState,
    scope: CoroutineScope,
    onCardItemClicked: (Card) -> Unit,
    onSelectedCardChange: (Card?) -> Unit,
    onSelectedPasswordChange: (Password?) -> Unit,
    onBottomSheetVisibleChange: (Boolean) -> Unit,
    onEvent: (event: CredentialEvent) -> Unit,
    selectedPasswordTag: String?,
    onSelectedPasswordTagChanged: (String?) -> Unit,
    onPasswordItemClicked: (Password) -> Unit,
) {


    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Header()
        Row(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                item {
                    CardHeader()
                }

                if (state.cards.isNotEmpty()) {
                    // Display all cards
                    items(state.cards) {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth(1f)
                        ) {
                            SecureHalfCardDisplay(it,
                                onCardItemClicked = { card ->
                                    onCardItemClicked(card)
                                },
                                onCardItemLongClicked = { card ->
                                    scope.launch {
                                        onSelectedCardChange(card)
                                        onSelectedPasswordChange(null)
                                        onBottomSheetVisibleChange(true)
                                    }
                                })
                        }
                    }

                } else {
                    // Display Add card option
                    item {
                        AddNewCard(onEvent)
                    }
                }

            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(2f)
            ) {
                item(span = { GridItemSpan(2) }) {
                    ManagePasswordHeader()
                }
                item(span = { GridItemSpan(2) }) {
                    DisplayRowPasswordTags(
                        selectedPasswordTag,
                        onEvent,
                        onSelectedPasswordTagChanged
                    )
                }
                item(span = { GridItemSpan(2) }) {
                    FilterPasswordDisplayHeader(selectedPasswordTag)
                }

                var list = state.passwords
                if (selectedPasswordTag != null && state.filteredPasswords != null) {
                    list = state.filteredPasswords
                }

                if (state.passwords.isNotEmpty()) {
                    items(list.size) {
                        val password = list.get(it)
                        // Password -> it
                        PasswordItemDisplay(
                            password,
                            onPasswordItemClicked,
                            scope,
                            onSelectedPasswordChange,
                            onSelectedCardChange,
                            onBottomSheetVisibleChange
                        )
                    }
                } else {
                    item(span = { GridItemSpan(2) }) {
                        // Display Add new password option
                        AddNewPasswordDisplay(onEvent)
                    }
                }


            }

//            LazyColumn(
//                modifier = Modifier.weight(1f)
//            ) {
//                item {
//                    ManagePasswordHeader()
//                }
//
//                item {
//                    DisplayRowPasswordTags(
//                        selectedPasswordTag,
//                        onEvent,
//                        onSelectedPasswordTagChanged
//                    )
//                }
//
//                item {
//                    FilterPasswordDisplayHeader(selectedPasswordTag)
//                }
//
//                var list = state.passwords
//                if (selectedPasswordTag != null && state.filteredPasswords != null) {
//                    list = state.filteredPasswords
//                }

//                if (state.passwords.isNotEmpty()) {
//                    items(list) {
//                        // Password -> it
//                        PasswordItemDisplay(
//                            it,
//                            onPasswordItemClicked,
//                            scope,
//                            onSelectedPasswordChange,
//                            onSelectedCardChange,
//                            onBottomSheetVisibleChange
//                        )
//                    }
//                } else {
//                    item {
//                        // Display Add new password option
//                        AddNewPasswordDisplay(onEvent)
//                    }
//                }
//            }
        }
    }
}


@Composable
private fun PasswordItemDisplay(
    it: Password,
    onPasswordItemClicked: (Password) -> Unit,
    scope: CoroutineScope,
    onSelectedPasswordChange: (Password?) -> Unit,
    onSelectedCardChange: (Card?) -> Unit,
    onBottomSheetVisibleChange: (Boolean) -> Unit,
) {
    PasswordSecureHalfDisplay(password = it,
        onPasswordItemClicked = {
            // onClick
            println("$TAG Clicked $it")
            onPasswordItemClicked(it)
        },
        onPasswordItemLongClicked = {
            scope.launch {
                onSelectedPasswordChange(it)
                onSelectedCardChange(null)
                onBottomSheetVisibleChange(true)
            }
        }
    )
}

@Composable
private fun DisplayRowPasswordTags(
    selectedPasswordTag: String?,
    onEvent: (event: CredentialEvent) -> Unit,
    onSelectedPasswordTagChanged: (String?) -> Unit,
) {
    val passwordTags = getPasswordTagsWithIcons()

    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.CenterStart)
    ) {
        items(passwordTags, key = {
            it.first
        }) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
            ) {
                PasswordTagCard(it, selectedPasswordTag) {
                    // onClick
                    if (selectedPasswordTag == null || selectedPasswordTag != it.first) {
                        onSelectedPasswordTagChanged(it.first)
                        onEvent(CredentialEvent.OnFilterChange(it.first))
                    } else {
                        onSelectedPasswordTagChanged(null)
                        onEvent(CredentialEvent.OnFilterChange("N/A"))
                    }
                }
            }
        }
    }
    space(8)
}

@Composable
private fun DisplayRowCards(
    state: CredentialState,
    scope: CoroutineScope,
    onCardItemClicked: (Card) -> Unit,
    onSelectedCardChange: (Card?) -> Unit,
    onSelectedPasswordChange: (Password?) -> Unit,
    onBottomSheetVisibleChange: (Boolean) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
//            .aspectRatio(2f)
//            .background(Color.Red)
    ) {
        items(state.cards) {
            Box(
                modifier = Modifier
                    .width(cardWidth)
                    .aspectRatio(1.75f)
                    .wrapContentSize()

            ) {
                SecureHalfCardDisplay(it,

                    onCardItemClicked = { card ->
                        onCardItemClicked(card)
                    },
                    onCardItemLongClicked = { card ->
                        scope.launch {
                            onSelectedCardChange(card)
                            onSelectedPasswordChange(null)
                            onBottomSheetVisibleChange(true)
                        }
                    })
            }
        }
    }
}

@Composable
private fun DisplayColumnCards(
    state: CredentialState,
    scope: CoroutineScope,
    onCardItemClicked: (Card) -> Unit,
    onSelectedCardChange: (Card?) -> Unit,
    onSelectedPasswordChange: (Password?) -> Unit,
    onBottomSheetVisibleChange: (Boolean) -> Unit,
) {

}

@Composable
private fun AddNewPasswordDisplay(onEvent: (event: CredentialEvent) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .aspectRatio(5.5f)
            .background(Color.Transparent)
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
            containerColor = getTextColor()
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
                ),
                color = getTextColorInverse()
            )
        }
    }
}

@Composable
private fun FilterPasswordDisplayHeader(selectedPasswordTag: String?) {
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
        color = getTextColor(),
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 5.dp)
    )

    space(8)
}

@Composable
private fun ManagePasswordHeader() {
    Text(
        text = "Manage Passwords",
        style = TextStyle(
            fontSize = 20.sp
        ),
        color = getTextColor(),
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 5.dp)
    )

    space(4)
}

@Composable
private fun AddNewCard(onEvent: (event: CredentialEvent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
//                .fillMaxWidth(0.7f)
                .width(cardWidth)
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
                    ),
                    color = getTextColorInverse()
                )
            }
        }
    }
}

@Composable
private fun CardHeader() {
    Text(
        text = "Your Cards",
        style = TextStyle(
            fontSize = 20.sp
        ),
        color = getTextColor(),
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 5.dp)
    )

    space(4)
}

@Composable
private fun Header() {
    Text(
        text = "Welcome ${getLoggedInUserName()}!",
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            fontSynthesis = FontSynthesis.Style
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 5.dp, end = 5.dp),
        color = getTextColor()
    )
    space(8)
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
