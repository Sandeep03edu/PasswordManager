package com.sandeep03edu.passwordmanager.manager.utils.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun BuildDesignedHeader(
    list: MutableList<String> = mutableListOf()
) {
    Text(
        buildAnnotatedString {
            list.forEach {currentLabel->
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 40.sp
                    )
                ) {
                    append("${currentLabel[0]}")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append("${currentLabel.subSequence(startIndex = 1, endIndex = currentLabel.length)} ")
                }

            }
        }
    )

}
