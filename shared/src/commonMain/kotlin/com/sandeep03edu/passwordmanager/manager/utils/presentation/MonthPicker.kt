package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.sandeep03edu.passwordmanager.space


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MonthPicker(
    visible: Boolean,
    currentMonth: Int,
    currentYear: Int,
    confirmButtonCLicked: (String, Int) -> Unit,
    cancelClicked: () -> Unit,
) {
    val months = listOf(
        "JAN",
        "FEB",
        "MAR",
        "APR",
        "MAY",
        "JUN",
        "JUL",
        "AUG",
        "SEP",
        "OCT",
        "NOV",
        "DEC"
    )

    var month by remember {
        mutableStateOf(months[currentMonth])
    }

    var year by remember {
        mutableStateOf(currentYear)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    if (visible) {
        AlertDialog(
            onDismissRequest = {

            },
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(10))
                .clip(RoundedCornerShape(10))
                .background(MaterialTheme.colorScheme.background),
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            content = {
                Column(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(10))
                        .clip(RoundedCornerShape(10))

                ) {

                    space(8)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year--
                                    }
                                ),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = year.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(-90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year++
                                    }
                                ),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null
                        )

                    }

                    Card(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background),
                        colors = CardDefaults.cardColors(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.background,
                        ),
                    ) {

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            content = {
                                items(months) {
                                    Box(
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clickable(
                                                indication = null,
                                                interactionSource = interactionSource,
                                                onClick = {
                                                    month = it
                                                }
                                            )
                                            .background(
                                                color = Color.Transparent
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        val animatedSize by animateDpAsState(
                                            targetValue = if (month == it) 60.dp else 0.dp,
                                            animationSpec = tween(
                                                durationMillis = 500,
                                                easing = LinearOutSlowInEasing
                                            )
                                        )

                                        Box(
                                            modifier = Modifier
                                                .size(animatedSize)
                                                .background(
                                                    color = if (month == it) Color.Blue else Color.Transparent,
                                                    shape = CircleShape
                                                )
                                        )

                                        Text(
                                            text = it,
                                            color = if (month == it) Color.White else Color.Black,
                                            fontWeight = FontWeight.Medium
                                        )

                                    }

                                }
                            }
                        )

//                        FlowRow(
//                            modifier = Modifier.fillMaxWidth(),
////                            mainAxisSpacing = 16.dp,
////                            crossAxisSpacing = 16.dp,
//                            horizontalArrangement = Arrangement.Center,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//
//                            months.forEach {
//                                Box(
//                                    modifier = Modifier
//                                        .size(60.dp)
//                                        .clickable(
//                                            indication = null,
//                                            interactionSource = interactionSource,
//                                            onClick = {
//                                                month = it
//                                            }
//                                        )
//                                        .background(
//                                            color = Color.Transparent
//                                        ),
//                                    contentAlignment = Alignment.Center
//                                ) {
//
//                                    val animatedSize by animateDpAsState(
//                                        targetValue = if (month == it) 60.dp else 0.dp,
//                                        animationSpec = tween(
//                                            durationMillis = 500,
//                                            easing = LinearOutSlowInEasing
//                                        )
//                                    )
//
//                                    Box(
//                                        modifier = Modifier
//                                            .size(animatedSize)
//                                            .background(
//                                                color = if (month == it) Color.Blue else Color.Transparent,
//                                                shape = CircleShape
//                                            )
//                                    )
//
//                                    Text(
//                                        text = it,
//                                        color = if (month == it) Color.White else Color.Black,
//                                        fontWeight = FontWeight.Medium
//                                    )
//
//                                }
//                            }
//
//                        }

                    }

                    space(8)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp, bottom = 30.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                        OutlinedButton(
                            modifier = Modifier.padding(end = 20.dp),
                            onClick = {
                                cancelClicked()
                            },
                            shape = CircleShape,
                            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.error),
//                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
                        ) {

                            Text(
                                text = "Cancel",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )

                        }

                        OutlinedButton(
                            modifier = Modifier.padding(end = 20.dp),
                            onClick = {
                                var monthStr = ""
                                if(months.indexOf(month)+1<10){
                                    monthStr = "0" + (months.indexOf(month)+1).toString()
                                }
                                else{
                                    monthStr =  (months.indexOf(month)+1).toString()
                                }

                                confirmButtonCLicked(
                                    monthStr,
                                    year%100
                                )
                            },
                            shape = CircleShape,
                            border = BorderStroke(1.dp, color = Color.Blue),
//                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
                        ) {

                            Text(
                                text = "OK",
                                color = Color.Blue,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )

                        }

                    }

                }
            }
        )
    }


}