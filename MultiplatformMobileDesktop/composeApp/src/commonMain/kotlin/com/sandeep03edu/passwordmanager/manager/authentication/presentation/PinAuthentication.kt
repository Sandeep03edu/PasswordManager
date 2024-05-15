package com.sandeep03edu.passwordmanager.manager.authentication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.manager.utils.presentation.CircularImage
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.ui.theme.getBackgroundColor
import com.sandeep03edu.passwordmanager.ui.theme.getTextColor


val TAG = "PinAuthenticationTag"

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PinAuthentication(
    pinLength: Int = 6,
    label: String,
    onComplete: (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val windowSizeClass = calculateWindowSizeClass()

        /*
                when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> {
                        ColumnPinAuthentication(label, pinLength, onComplete = {
                            onComplete(it)
                        })
                    }

                    WindowWidthSizeClass.Medium , WindowWidthSizeClass.Expanded-> {
                        RowPinAuthentication(label, pinLength, onComplete = {
                            onComplete(it)
                        })
                    }
                }
        */

        when (windowSizeClass.heightSizeClass) {
            WindowHeightSizeClass.Compact -> {
                RowPinAuthentication(label, pinLength, onComplete = {
                    onComplete(it)
                })
            }

            WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> {
                ColumnPinAuthentication(label, pinLength, onComplete = {
                    onComplete(it)
                })
            }
        }
    }
}

fun generateShuffledList(): MutableList<Int> {
    val list: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
    return list.shuffled().toMutableList()
}

@Composable
private fun RowPinAuthentication(
    label: String,
    pinLength: Int,

    onComplete: (String) -> Unit,
) {
    var currentIndex by remember { mutableStateOf(0) }

    val values = remember { mutableStateListOf<Int>() }
    while (values.size < pinLength) {
        values.add(-1)
    }

    val list by remember { mutableStateOf(generateShuffledList()) }

    val lastElem = list.get(9)
    list[9] = -2
    list.add(lastElem)
    list.add(-1)

    println("$TAG Listt:: $list")

    Row(
        modifier = Modifier.fillMaxSize()
            .background(getBackgroundColor())

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth(0.5f)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // TODO : Remove with app logo
            CircularImage(
                painter = paintResource(SharedRes.images.avatar),
                modifier = Modifier
                    .size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Enter your $label",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily.SansSerif,
                    ),
                    color = getTextColor()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .weight(0.1f),
                verticalAlignment = Alignment.Top,
            ) {
                repeat(pinLength) {
                    CircularDot(it, values)
                }
            }

            Spacer(
                modifier = Modifier.fillMaxWidth()
                    .weight(0.1f),
            )

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth(1f)
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f),
                columns = GridCells.Fixed(3),
                content = {
                    items(list.size) {
                        CircularNumber(list.get(it), onClick = {
                            if (values.size != 0) {

                                if (it == -1) {
                                    if (currentIndex != 0) {
                                        currentIndex--;
                                        values.set(currentIndex, -1)
                                    }
                                } else if (currentIndex < pinLength) {
                                    values.set(currentIndex, it)
                                    currentIndex++
                                }

                                println("$TAG Values:: ${values.toList()}")

                                if (currentIndex == pinLength) {
                                    var ans = ""
                                    var i = 0

                                    println("$TAG Complete Values:: ${values.toList()} || Curr:: $currentIndex || pinLength:: $pinLength")

                                    repeat(pinLength) {
                                        ans += values[i].toString()
                                        values[i] = -1;
                                        i++;
                                    }

//                                    values.clear()

                                    currentIndex = 0

                                    onComplete(ans)

                                }
                            }
                        })
                    }
                },
                verticalArrangement = Arrangement.Bottom,
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}


@Composable
private fun ColumnPinAuthentication(
    label: String,
    pinLength: Int,
    onComplete: (String) -> Unit,
) {
    var currentIndex by remember { mutableStateOf(0) }

    val values = remember { mutableStateListOf<Int>() }
    while (values.size < pinLength) {
        values.add(-1)
    }

    val list by remember { mutableStateOf(generateShuffledList()) }
//    val list  = (generateShuffledList())

    val lastElem = list.get(9)

    if (list.size < 12) {
        list[9] = -2
        list.add(lastElem)
        list.add(-1)
    }

    println("$TAG List:: $list")


// TODO : Remove auto verification
//**********************************************************************//
        currentIndex = 0
        repeat(pinLength){
            values.set(currentIndex, it + 1)
            currentIndex++
            println("$TAG It:: $it")
        }
        var ans = ""
        var i = 0
        repeat(pinLength) {
            ans += values[i].toString()
            values[i] = -1;
            i++;
        }
        currentIndex = 0
        onComplete(ans)
//**********************************************************************//

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(getBackgroundColor())
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // TODO : Remove with app logo
        CircularImage(
            painter = paintResource(SharedRes.images.avatar),
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Enter your $label",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif,
                ),
                color = getTextColor()

            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .weight(0.1f),
            verticalAlignment = Alignment.Top,
        ) {
            repeat(pinLength) {
                CircularDot(it, values)
            }
        }

        Spacer(
            modifier = Modifier.fillMaxWidth()
                .weight(0.1f),
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            columns = GridCells.Fixed(3),
            content = {
                items(list.size) {
                    CircularNumber(list.get(it), onClick = {
                        if (values.size != 0) {

                            if (it == -1) {
                                if (currentIndex != 0) {
                                    currentIndex--;
                                    values[currentIndex] = -1
                                }
                            } else if (currentIndex < pinLength) {
                                values[currentIndex] = it
                                currentIndex++
                            }

                            println("$TAG Values:: ${values.toList()} +++ CurrentIdx: ${currentIndex}")


                            if (currentIndex == pinLength) {
                                var ans = ""
                                var i = 0

                                println("$TAG Complete Values:: ${values.toList()} || Curr:: $currentIndex || pinLength:: $pinLength")

                                repeat(pinLength) {
                                    ans += values[i].toString()
                                    values[i] = -1;
                                    i++;
                                }

//                                    values.clear()

                                currentIndex = 0

                                onComplete(ans)

                            }
                        }
                    })
                }
            },
            verticalArrangement = Arrangement.Bottom,
        )

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun CircularNumber(
    value: Int,
    onClick: (Int) -> Unit,
) {
    if (value == -2) {
        return
    }
    Box(modifier = Modifier) {
        if (value != -1) {
            Box(
                modifier = Modifier
                    .width(55.dp)
                    .padding(5.dp)
                    .aspectRatio(1f)
                    .border(1.dp, Color.Black, RoundedCornerShape(25.dp))
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.Center)
                    .clickable {
                        onClick(value)
                    }
            ) {
                Text(
                    text = "$value",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                    ),

                    )
            }
        } else {
            Box(
                modifier = Modifier
                    .width(55.dp)
                    .padding(5.dp)
                    .aspectRatio(1f)
                    .align(Alignment.Center)
                    .background(Color.Transparent)

            ) {
                Image(
                    imageVector = Icons.Default.Backspace,
                    "Error",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            onClick(value)
                        },
                    colorFilter = ColorFilter.tint(getTextColor())
                )
            }
        }
    }
}

@Composable
fun CircularDot(
    index: Int,
    values: List<Int>,
) {
    var backgroundColor = MaterialTheme.colorScheme.background
    if (values[index] != -1) {
        backgroundColor = MaterialTheme.colorScheme.primary
    }
    Box(
        modifier = Modifier.size(23.dp)
            .padding(horizontal = 3.dp)
            .aspectRatio(1f)
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .clip(CircleShape)
            .clipToBounds()
            .background(color = backgroundColor)
    )
}