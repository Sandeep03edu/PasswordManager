package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeep03edu.passwordmanager.space

@Composable
fun AlertDialogBox(
    modifier: Modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .widthIn(0.dp, 400.dp)
        .heightIn(0.dp, 500.dp),
    title: String,
    description: String = "",
    onYesClick: (() -> Unit)? = null,
    onNoClick: (() -> Unit)? = null,
) {

    OutlinedCard  (
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(10),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
        ) {
            item {
                if (title.trim().isNotEmpty()) {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                space(4)
            }
            item {
                if (description.trim().isNotEmpty()) {
                    Text(
                        text = description,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )
                }
                space(16)
            }

            item {
                Row(
                    modifier = Modifier.fillParentMaxWidth(0.6f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (onYesClick != null) {
                        Box(
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.onBackground,
                                    RoundedCornerShape(10.dp)
                                )
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .clickable {
                                    onYesClick()
                                },
                        ) {
                            Text("Yes")
                        }
                    }
                    space(width = 8)
                    if (onNoClick != null) {
                        Box(
                            modifier = Modifier
//                            .fillMaxWidth(0.4f)
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.onBackground,
                                    RoundedCornerShape(10.dp)
                                )
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .clickable {
                                    onNoClick()
                                },
                        ) {
                            Text("No")
                        }
                    }
                }
            }
        }
    }
}