package com.sandeep03edu.passwordmanager.manager.credentials.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.utils.data.getCardTypeLogo
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space

@Composable
fun SecureHalfCardDisplay(
    card: Card,
    onCardItemClicked: (Card) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(5.dp)
            .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                onCardItemClicked(card)
            }
            .padding(10.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            space(4)

            Image(
                painter = paintResource(getCardTypeLogo(card.issuerName)),
                null,
                modifier = Modifier.width(100.dp)
                    .height(55.dp)
                    .padding(vertical = 5.dp, horizontal = 0.dp)
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

@Composable
fun UpperHalfCardDisplay(
    card: Card,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .aspectRatio(2f)
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 15.dp, horizontal = 15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            space(4)

            Text(modifier = Modifier
                .align(Alignment.CenterHorizontally),
                text = buildAnnotatedString
                {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 24.sp
                        )
                    ) {
                        append(
                            "${
                                card.issuerName.uppercase()
                                    .subSequence(0, card.issuerName.length / 2)
                            }"
                        )
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append(
                            "${
                                card.issuerName.uppercase().subSequence(
                                    card.issuerName.length / 2,
                                    card.issuerName.length
                                )
                            }"
                        )
                    }
                }
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = card.cardType.toString(),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Image(
                    painter = paintResource(getCardTypeLogo(card.issuerName)),
                    null,
                    modifier = Modifier.width(100.dp)
                        .height(40.dp),
                    alignment = Alignment.CenterEnd
                )
            }

        }

    }
}

@Composable
fun BottomHalfCardDisplay(
    card: Card,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 15.dp, horizontal = 15.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            space(4)

            Text(
                buildAnnotatedString
                {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 24.sp
                        )
                    ) {
                        append(
                            "${
                                card.cardHolderName.uppercase()
                                    .subSequence(0, card.cardHolderName.length / 2)
                            }"
                        )
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append(
                            "${
                                card.cardHolderName.uppercase().subSequence(
                                    card.cardHolderName.length / 2,
                                    card.cardHolderName.length
                                )
                            }"
                        )
                    }
                }
            )

            space(8)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Issue",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    space(1)

                    Text(
                        text = card.issueDate.toString(),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )


                    space(8)

                    Text(
                        text = "Expiry",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    space(1)

                    Text(
                        text = card.expiryDate.toString(),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                }

                space(width = 8)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "CVV",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    space(1)

                    Text(
                        text = card.cvv,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    space(8)

                    Text(
                        text = "Pin",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    space(1)

                    Text(
                        text = card.pin,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

            }
        }

    }

}
