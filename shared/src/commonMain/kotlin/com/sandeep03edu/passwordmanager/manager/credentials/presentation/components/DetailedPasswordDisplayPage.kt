package com.sandeep03edu.passwordmanager.manager.credentials.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.CredentialEvent
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.CredentialState
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.CredentialViewModel
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.utils.data.getPasswordTags
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconLabeledTextField
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconLabeledTextField
import com.sandeep03edu.passwordmanager.manager.utils.presentation.IconLabeledTextField
import com.sandeep03edu.passwordmanager.space


data class DetailedPasswordDisplayPageClass(
    val appModule: AppModule,
    val password: Password
) : Screen{

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { CredentialViewModel(appModule.credentialDataSource) }
        val state by viewModel.state.collectAsState()
        val onEvent = viewModel::onEvent
        DetailedPasswordDisplayPage(viewModel, state, onEvent,password)
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailedPasswordDisplayPage(
    viewModel: CredentialViewModel,
    state: CredentialState,
    onEvent: (event: CredentialEvent) -> Unit,
    password: Password
) {

    Scaffold(
        floatingActionButton = {
            if (!state.isAddNewCredentialSheetOpen) {
                FloatingActionButton(
                    onClick = {
                        onEvent(CredentialEvent.OnDisplayAddNewDataClick)
                    },
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Add contact",
                    )
                }
            }
        }
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        )  {
            item {
                Text(
                    buildAnnotatedString
                    {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 40.sp
                            )
                        ) {
                            append("Pass")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        ) {
                            append("word")
                        }
                    }
                )

                space(16)
            }
            
           item{
               Text(
                   text = "Password Details",
                   textAlign = TextAlign.Start,
                   modifier = Modifier.fillMaxWidth()
                       .padding(horizontal = 4.dp)
               )

               space(4)

               IconLabeledTextField(
                   leadingIcon = Icons.Rounded.Person,
                   label = "Title",
                   text = password.title ?: "",
               )

               space(4)

               IconLabeledTextField(
                   leadingIcon = Icons.Rounded.Person,
                   label = "Url",
                   text = password.url ?: "",
                   prefix = "https://",
               )

               space(16)

               Text(
                   text = "User Details",
                   textAlign = TextAlign.Start,
                   modifier = Modifier.fillMaxWidth()
                       .padding(horizontal = 4.dp)
               )

               space(4)

               IconLabeledTextField(
                   leadingIcon = Icons.Rounded.Person,
                   label = "Username",
                   text = password.username ?: "",
               )

               space(4)

               IconLabeledTextField(
                   leadingIcon = Icons.Rounded.Person,
                   label = "EmailId",
                   text = password.email ?: "",
               )

               space(16)

               Text(
                   text = "Security Keys",
                   textAlign = TextAlign.Start,
                   modifier = Modifier.fillMaxWidth()
                       .padding(horizontal = 4.dp)
               )

               space(4)

               IconLabeledTextField(
                   leadingIcon = Icons.Rounded.Person,
                   label = "Password",
                   text = password.password ?: "",
               )

               space(4)

               IconLabeledTextField(
                   leadingIcon = Icons.Rounded.Person,
                   label = "Pin",
                   text = password.pin ?: "",
               )

               space(16)

               Text(
                   text = "Tags",
                   textAlign = TextAlign.Start,
                   modifier = Modifier.fillMaxWidth()
                       .padding(horizontal = 4.dp)
               )

               space(4)

               FlowRow(
               ) {
                   password.tags.forEach {
                       TagCard(it, false) {}
                   }
               }




               space(4)

           }

        }
    }

}