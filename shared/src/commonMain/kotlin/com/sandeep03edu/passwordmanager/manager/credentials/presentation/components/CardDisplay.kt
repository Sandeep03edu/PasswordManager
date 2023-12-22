package com.sandeep03edu.passwordmanager.manager.credentials.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.TAG
import com.sandeep03edu.passwordmanager.manager.utils.data.getCardIssuerLogo
import com.sandeep03edu.passwordmanager.manager.utils.data.getCardTypeLogo
import com.sandeep03edu.passwordmanager.manager.utils.data.getCredentialUploadImage
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space
import dev.icerock.moko.resources.ImageResource
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SecureHalfCardDisplay(
    card: Card,
    onCardItemClicked: (Card) -> Unit,
    onCardItemLongClicked: (Card) -> Unit,
) {
    Card  (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(5.dp)
            .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .combinedClickable(
                onClick = {
                    onCardItemClicked(card)
                },
                onLongClick = {
                    onCardItemLongClicked(card)
                },
            )
            .padding(10.dp),
    ) {

        Column(horizontalAlignment = Alignment.Start) {
            space(4)

            Image(
                painter = paintResource(getCardTypeLogo(card.cardType)),
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

                println("$TAG Card:: $card")
                Image(
                    painter = paintResource(getCredentialUploadImage(card.isSynced)),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    alignment = Alignment.BottomEnd
                )

            }
        }

    }
}

@Composable
fun UpperHalfCardDisplay(
    card: Card,
    background: ImageResource,
    textColor: Color = Color.White,
    textHeaderColor: Color = Color.Yellow,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .aspectRatio(1.5f)
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .paint(
                paintResource(background),
                contentScale = ContentScale.Fit
            )
//            .background(MaterialTheme.colorScheme.primaryContainer)
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
                            color = textHeaderColor,
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
                            color = textColor
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

            space(16)

            val cardNumber = card.cardNumber

            DisplayCardNumber(cardNumber, textColor)

            space(16)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

//                Text(
//                    text = card.cardType.toString(),
//                    style = TextStyle(
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                )

                Image(
                    painter = paintResource(getCardIssuerLogo(card.issuerName)),
                    null,
                    modifier = Modifier.width(100.dp),
                    alignment = Alignment.CenterStart,
//                    contentScale = ContentScale.Fit
                )

                Image(
                    painter = paintResource(getCardTypeLogo(card.cardType!!)),
                    null,
                    modifier = Modifier.width(100.dp),
                    alignment = Alignment.CenterEnd,
//                    contentScale = ContentScale.FillWidth
                )
            }

        }

    }
}

@Composable
fun DisplayCardNumber(
    cardNumber: String,
    textColor: Color,
) {
    val length = cardNumber.length
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {

        var start = 0;
        // Divide card number in max 4 parts
        val division = ceil((length / 4f)).toInt()
        val repeater = min(division, 4)
        repeat(repeater) {
            Text(
                text = cardNumber.subSequence(start, min(start + max(4, division), length)).toString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
            )
            start +=  max(4, division)
        }
    }
}

@Composable
fun BottomHalfCardDisplay(
    card: Card,
    background: ImageResource,
    textColor: Color = Color.White,
    textHeaderColor: Color = Color.Green,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .aspectRatio(1.5f)
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .paint(
                paintResource(background),
                contentScale = ContentScale.Fit
            )
//            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 15.dp, horizontal = 15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            space(4)

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = buildAnnotatedString
                {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.ExtraBold,
                            color = textHeaderColor,
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
                            color = textColor
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
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Issue",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                    )

                    space(1)

                    Text(
                        text = card.issueDate.toString(),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                    )


                    space(8)

                    Text(
                        text = "Expiry",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                    )

                    space(1)

                    Text(
                        text = card.expiryDate.toString(),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                    )

                }

                space(width = 8)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "CVV",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                    )

                    space(1)

                    Text(
                        text = card.cvv,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor

                        )
                    )

                    space(8)

                    Text(
                        text = "Pin",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor

                        )
                    )

                    space(1)

                    Text(
                        text = card.pin,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor

                        )
                    )
                }

            }

            space(8)
        }

    }

}
