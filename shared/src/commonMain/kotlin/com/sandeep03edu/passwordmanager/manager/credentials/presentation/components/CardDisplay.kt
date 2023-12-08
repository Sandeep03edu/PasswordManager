package com.sandeep03edu.passwordmanager.manager.credentials.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.utils.data.getCardLogo
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space

@Composable
fun SecureHalfCardDisplay(
    card: Card,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(5.dp)
            .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            space(4)

            Image(
                painter = paintResource(getCardLogo(card.issuerName)),
                null,
                modifier = Modifier.width(100.dp),
            )

            space(8)

            val cardNumber = card.cardNumber
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(3) {
                    Text(
                        text = "****",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    space(width = 15)
                }

                val securedCardNumber = cardNumber.substring(
                    range = IntRange(
                        cardNumber.length - 4,
                        cardNumber.length - 1
                    )
                )

                Text(
                    text = securedCardNumber,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

            }

            space(4)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = card.cardHolderName,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                var date = ""
                if (card.expiryDate != null && card.expiryDate!!.isNotEmpty()) {
                    date = card.expiryDate!!
                } else if (card.issueDate != null && card.issueDate!!.isNotEmpty()) {
                    date = card.issueDate!!
                }

                space(width = 15)

                Text(
                    text = date,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

    }
}