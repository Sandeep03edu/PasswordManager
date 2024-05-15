package com.sandeep03edu.passwordmanager.manager.credentials.presentation.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sandeep03edu.passwordmanager.manager.credentials.data.cardPromotionList
import com.sandeep03edu.passwordmanager.manager.credentials.data.passwordPromotionList
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
import com.sandeep03edu.passwordmanager.ui.theme.getCardColorShades
import com.sandeep03edu.passwordmanager.ui.theme.getFloatingActionButtonColor
import com.sandeep03edu.passwordmanager.ui.theme.getPasswordHalfDisplayBackground
import com.sandeep03edu.passwordmanager.ui.theme.getTextColor
import com.sandeep03edu.passwordmanager.ui.theme.getTextColorInverse
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

var cardWidth = 280.dp
var numberOfPasswords = 3
var isRowView = false

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
                // For all height it is same
                numberOfPasswords = 1
                cardWidth = 280.dp
                isRowView = false
            }

            WindowWidthSizeClass.Medium -> {
                cardWidth = 300.dp
                when (windowSizeClass.heightSizeClass) {
                    WindowHeightSizeClass.Compact -> {
                        isRowView = true
                        numberOfPasswords = 1
                    }

                    WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> {
                        numberOfPasswords = 2
                        isRowView = false
                    }
                }
            }

            WindowWidthSizeClass.Expanded -> {
                cardWidth = 320.dp
                when (windowSizeClass.heightSizeClass) {
                    WindowHeightSizeClass.Compact -> {
                        isRowView = true
                        numberOfPasswords = 2
                    }

                    WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> {
                        isRowView = false
                        numberOfPasswords = 3
                    }
                }
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
                    containerColor = getFloatingActionButtonColor(),

                    ) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        contentDescription = "Add contact",
                        tint = getTextColorInverse()
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

        Column(
            modifier = Modifier.fillMaxSize()
                .background(getBackgroundColor()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            if (isRowView) {
                // Row View
                DisplayRowView(
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
            } else {
                // Column View
                DisplayColumnView(
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

        AddDataSheet(
            state = state,
            onEvent = onEvent,
            newCard = newCard,
            newPassword = newPassword
        )

    }
}

@Composable
private fun DisplayColumnView(
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(numberOfPasswords),
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {

        item(span = { GridItemSpan(numberOfPasswords) }) {
            Header()
        }
        item(span = { GridItemSpan(numberOfPasswords) }) {
            CardHeader()
        }

        item(span = { GridItemSpan(numberOfPasswords) }) {
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


        item(span = { GridItemSpan(numberOfPasswords) }) {
            ManagePasswordHeader()
        }

        item(span = { GridItemSpan(numberOfPasswords) }) {
            DisplayRowPasswordTags(
                selectedPasswordTag,
                onEvent,
                onSelectedPasswordTagChanged
            )
        }

        item(span = { GridItemSpan(numberOfPasswords) }) {
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
            items(passwordPromotionList.size) {
                // Display Add new password option
                AddNewPasswordDisplay(
                    onEvent = onEvent,
                    passwordPromotion = passwordPromotionList[it],
                )
            }
        }
    }
}

@Composable
private fun DisplayRowView(
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
                    .width(cardWidth + 10.dp)
            ) {
                item {
                    CardHeader()
                }

                if (state.cards.isNotEmpty()) {
                    // Display all cards in column view
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

                } else {
                    // Display Add card option
                    /*
                                        item {
                                            AddNewCard(onEvent)
                                        }
                    */
                    items(cardPromotionList.size) {
                        Box(
                            modifier = Modifier
                                .width(cardWidth)
                                .aspectRatio(1.75f)
                                .wrapContentSize()
                                .padding(5.dp)
                                .clip(RoundedCornerShape(10.dp))

                        ) {
                            val cardShades = getCardColorShades()
                            val cardBkgColor = cardShades[0]
                            val arc1Color = cardShades[1]
                            val arc2Color = cardShades[2]

                            Canvas(
                                modifier = Modifier.fillMaxSize()
                            ) {

                                val canvasWidth = size.width + 20.dp.toPx()
                                val canvasHeight = size.height + 20.dp.toPx()

                                drawRoundRect(
                                    color = cardBkgColor,
                                    topLeft = Offset(-10.dp.toPx(), -10.dp.toPx()),
                                    size = Size(canvasWidth, canvasHeight),
                                    cornerRadius = CornerRadius(x = 10f, y = 10f)
                                )

                                drawArc(
                                    color = arc1Color,
                                    startAngle = 180f,
                                    sweepAngle = 180f,
                                    useCenter = true,
                                    topLeft = Offset(
                                        -5.dp.toPx(),
                                        canvasHeight * 0.5f + 10.dp.toPx()
                                    ),
                                    size = Size(
                                        canvasWidth - 60.dp.toPx(),
                                        canvasHeight - 40.dp.toPx()
                                    ),
                                )

                                drawArc(
                                    color = arc2Color,
                                    startAngle = 90f,
                                    sweepAngle = 180f,
                                    useCenter = true,
                                    topLeft = Offset(canvasWidth - 120.dp.toPx(), -40.dp.toPx()),
                                    size = Size(canvasWidth + 60.dp.toPx(), canvasHeight * 2f),
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                                    .clickable {
                                        onEvent(
                                            CredentialEvent.OnDisplayAddEditNewDataClick(
                                                Password(),
                                                Card()
                                            )
                                        )
                                    }
                                    .padding(10.dp)
                            ) {
                                Image(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null
                                )

                                space(8)

                                Text(
                                    text = cardPromotionList[it],
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = getTextColorInverse(),
                                    textAlign = TextAlign.Center

                                )
                            }
                        }
                    }
                }

                item {
                    space(16)
                }

            }

            LazyVerticalGrid(
                modifier = Modifier.weight(numberOfPasswords.toFloat()),
                columns = GridCells.Fixed(numberOfPasswords)
            ) {
                item(span = { GridItemSpan(numberOfPasswords) }) {
                    ManagePasswordHeader()
                }

                item(span = { GridItemSpan(numberOfPasswords) }) {
                    DisplayRowPasswordTags(
                        selectedPasswordTag,
                        onEvent,
                        onSelectedPasswordTagChanged
                    )
                }

                item(span = { GridItemSpan(numberOfPasswords) }) {
                    FilterPasswordDisplayHeader(selectedPasswordTag)
                }

                var list = state.passwords
                if (selectedPasswordTag != null && state.filteredPasswords != null) {
                    list = state.filteredPasswords
                }
                if (state.passwords.isNotEmpty()) {
                    items(list.size) {
                        val password = list[it]
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
                    items(passwordPromotionList.size) {
                        // Display Add new password option
                        AddNewPasswordDisplay(
                            onEvent = onEvent,
                            passwordPromotion = passwordPromotionList[it],
                        )
                    }
                }


            }
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
private fun AddNewPasswordDisplay(
    onEvent: (event: CredentialEvent) -> Unit,
    passwordPromotion: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(8.dp)
//            .dashedBorder(2.dp, Color.Red, 8.dp)
            .clickable {
                onEvent(
                    CredentialEvent.OnDisplayAddEditNewDataClick(
                        Password(),
                        Card()
                    )
                )
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = getPasswordHalfDisplayBackground()
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null
            )

            space(width = 8)

            Text(
                text = passwordPromotion,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = getTextColorInverse(),
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

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
//            .aspectRatio(2f)
//            .background(Color.Red)
    ) {
        items(cardPromotionList.size) {
            Box(
                modifier = Modifier
                    .width(cardWidth)
                    .aspectRatio(1.75f)
                    .wrapContentSize()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))

            ) {
                val cardShades = getCardColorShades()
                val cardBkgColor = cardShades[0]
                val arc1Color = cardShades[1]
                val arc2Color = cardShades[2]

                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {

                    val canvasWidth = size.width + 20.dp.toPx()
                    val canvasHeight = size.height + 20.dp.toPx()

                    drawRoundRect(
                        color = cardBkgColor,
                        topLeft = Offset(-10.dp.toPx(), -10.dp.toPx()),
                        size = Size(canvasWidth, canvasHeight),
                        cornerRadius = CornerRadius(x = 10f, y = 10f)
                    )

                    drawArc(
                        color = arc1Color,
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = true,
                        topLeft = Offset(-5.dp.toPx(), canvasHeight * 0.5f + 10.dp.toPx()),
                        size = Size(canvasWidth - 60.dp.toPx(), canvasHeight - 40.dp.toPx()),
                    )

                    drawArc(
                        color = arc2Color,
                        startAngle = 90f,
                        sweepAngle = 180f,
                        useCenter = true,
                        topLeft = Offset(canvasWidth - 120.dp.toPx(), -40.dp.toPx()),
                        size = Size(canvasWidth + 60.dp.toPx(), canvasHeight * 2f),
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                        .clickable {
                            onEvent(
                                CredentialEvent.OnDisplayAddEditNewDataClick(
                                    Password(),
                                    Card()
                                )
                            )
                        }
                        .padding(10.dp)
                ) {
                    Image(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null
                    )

                    space(8)

                    Text(
                        text = cardPromotionList[it],
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = getTextColorInverse(),
                        textAlign = TextAlign.Center
                    )
                }
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
