package com.sandeep03edu.passwordmanager.manager.credentials.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeep03edu.passwordmanager.SharedRes
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.TAG
import com.sandeep03edu.passwordmanager.paintResource
import com.sandeep03edu.passwordmanager.space
import io.kamel.core.getOrNull
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun PasswordSecureHalfDisplay(
    password: Password,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .clickable {
                onClick()
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxSize()
                .padding(10.dp)
        ) {
            val painter =
                asyncPainterResource(data = "https://www.google.com/s2/favicons?sz=64&domain_url=${password.url}")


            if (painter.getOrNull() != null && painter.getOrNull()?.intrinsicSize?.height?.toInt() == 64) {
                KamelImage(
                    resource = painter,
                    onLoading = { progress -> CircularProgressIndicator(progress) },
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            } else {
                // TODO : Replace with app icon
                Image(
                    painter = paintResource(SharedRes.images.avatar),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )
            }

            space(width = 16)

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = password.title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                space(4)

                Text(
                    text = if (password.email.isNotEmpty()) password.email else password.username,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

    }
}

