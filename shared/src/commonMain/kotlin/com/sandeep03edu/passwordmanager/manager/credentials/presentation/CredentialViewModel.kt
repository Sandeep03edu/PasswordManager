package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.CardValidator
import com.sandeep03edu.passwordmanager.manager.credentials.domain.CredentialDataSource
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.domain.PasswordValidator
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


val TAG = "CredentialViewModelTag"

@OptIn(ExperimentalCoroutinesApi::class)
class CredentialViewModel(private val credentialDataSource: CredentialDataSource) : ScreenModel {

    private val _state = MutableStateFlow(CredentialState())

//    var filterTag: String by mutableStateOf("N/A")
//        private set

    // Not updating
    private val _filterTag = MutableStateFlow("N/A")
    private val filterTag: StateFlow<String> get() = _filterTag


    private val filteredPasswords: StateFlow<List<Password>> = _filterTag.flatMapLatest { tag ->
        val pass = credentialDataSource.getFilteredPasswords(tag)
        println("$TAG Inside FilterPass: $tag ->> ${pass}")
        pass
    }.stateIn(
        screenModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    val state = combine(
        _state,
        credentialDataSource.getCards(),
        credentialDataSource.getPasswords(),
        filteredPasswords,
    ) { state, cards, passwords, filteredPasswords ->

        // Updated Value
        println("$TAG FilterTag in State: ${filterTag.value}")
        println("$TAG FilterTag ${filterTag.value} --> $filteredPasswords")

        println(
            "$TAG Data:: ${
                credentialDataSource.getFilteredPasswords(
                    filterTag.value
                ).firstOrNull()
            }"
        )

        state.copy(
            cards = cards,
            passwords = passwords,
            filteredPasswords = filteredPasswords
        )

    }
        .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000L), CredentialState())


    var newCard: Card? by mutableStateOf(null)
        private set

    var newPassword: Password? by mutableStateOf(null)
        private set

    fun onEvent(event: CredentialEvent) {
        when (event) {
            is CredentialEvent.OnCardPasswordOptionSelected -> {
                screenModelScope.launch {
                    val value = event.value
                    if (value == "Card") {
                        newPassword = null
                    } else {
                        newCard = null
                    }

                    /*
                                        _state.update {
                                            it.copy(
                                                isAddNewCredentialSheetOpen = false,

                                                // Reset all password error
                                                passwordTitleError = null,
                                                passwordUrlError = null,
                                                passwordSecurityKeyError = null,
                                                passwordUserDetailError = null,
                                                passwordTagsError = null,

                                                // Reset all card error
                                                cardNumberError = null,
                                                cardTypeError = null,
                                                cardHolderNameError = null,
                                                cardDateError = null,
                                                cardBankNameError = null,
                                                cardSecurityKeyError = null
                                            )
                                        }
                    */
                }
            }

            is CredentialEvent.OnDismissAddNewDataClick -> {
                screenModelScope.launch {
                    _state.update {
                        it.copy(
                            isAddNewCredentialSheetOpen = false
                        )
                    }
                }
            }

            is CredentialEvent.OnDisplayAddNewDataClick -> {
                screenModelScope.launch {
                    _state.update {
                        it.copy(
                            isAddNewCredentialSheetOpen = true
                        )
                    }

                    newCard = Card()
                    newPassword = Password()
                }
            }

            is CredentialEvent.OnCardIssuerNameChanged -> {
                println("$TAG OnCardIssuerNameChanged Started!!")
                newCard = newCard?.copy(
                    issuerName = event.value
                )

                println("$TAG OnCardIssuerNameChanged New card $newCard")
            }

            is CredentialEvent.OnCardCvvChanged -> {
                newCard = newCard?.copy(
                    cvv = event.value
                )
            }

            is CredentialEvent.OnCardExpiryDateChanged -> {
                newCard = newCard?.copy(
                    expiryDate = event.value
                )
            }

            is CredentialEvent.OnCardHolderNameChanged -> {
                newCard = newCard?.copy(
                    cardHolderName = event.value
                )
                println("$TAG Card Holder name Changed name to ${event.value}")
                println("$TAG Card Holder name::: Card: $newCard")
            }

            is CredentialEvent.OnCardIssueDateChanged -> {
                newCard = newCard?.copy(
                    issueDate = event.value
                )
            }

            is CredentialEvent.OnCardNumberChanged -> {
                newCard = newCard?.copy(
                    cardNumber = event.value
                )
            }

            is CredentialEvent.OnCardPinChanged -> {
                newCard = newCard?.copy(
                    pin = event.value
                )
            }

            is CredentialEvent.OnCardTypeChanged -> {
                newCard = newCard?.copy(
                    cardType = event.value
                )
            }

            is CredentialEvent.OnPasswordEmailIdChange -> {
                newPassword = newPassword?.copy(
                    email = event.value
                )
            }

            is CredentialEvent.OnPasswordPasswordChange -> {
                newPassword = newPassword?.copy(
                    password = event.value
                )
            }

            is CredentialEvent.OnPasswordPinChange -> {
                newPassword = newPassword?.copy(
                    pin = event.value
                )
            }

            is CredentialEvent.OnPasswordTagChange -> {
                val newTags = newPassword?.tags
                val tag = event.value

                if (newTags?.contains(tag) == true) {
                    newTags.remove(tag)
                } else if (newTags?.contains(tag) == false) {
                    newTags.add(tag)
                }

                println("$TAG Tags : $newTags")

                newPassword = newTags?.let {
                    newPassword?.copy(
                        // TODO: How to add Tags????
                        tags = it
                    )
                }
            }

            is CredentialEvent.OnPasswordTitleChange -> {
                newPassword = newPassword?.copy(
                    title = event.value
                )
            }

            is CredentialEvent.OnPasswordUrlChange -> {
                newPassword = newPassword?.copy(
                    url = event.value
                )
            }

            is CredentialEvent.OnPasswordUsernameChange -> {
                newPassword = newPassword?.copy(
                    username = event.value
                )
            }

            CredentialEvent.SaveCard -> {
                println("$TAG Saving Card:: $newCard")
                newCard?.let { card ->
                    val cardValidator = CardValidator.validateCard(card)

                    val errors = listOfNotNull(
                        cardValidator.issuerNameError,
                        cardValidator.cardNumberError,
                        cardValidator.cardTypeError,
                        cardValidator.cardHolderNameError,
                        cardValidator.dateError,
                        cardValidator.securityKeyError,
                    )

                    println("$TAG New Card errors: $errors")

                    if (errors.isEmpty()) {
                        _state.update {
                            it.copy(
                                isAddNewCredentialSheetOpen = false,

                                // Reset all password error
                                passwordTitleError = null,
                                passwordUrlError = null,
                                passwordSecurityKeyError = null,
                                passwordUserDetailError = null,
                                passwordTagsError = null,

                                // Reset all card error
                                cardNumberError = null,
                                cardHolderNameError = null,
                                cardTypeError = null,
                                cardDateError = null,
                                cardIssuerNameError = null,
                                cardSecurityKeyError = null
                            )
                        }

                        println("$TAG Saving Card : $card")

                        screenModelScope.launch {
                            credentialDataSource.addCard(card)
                            delay(300L)
                            newCard = null
                            newPassword = null
                        }
                    } else {
                        _state.update {
                            it.copy(
                                // Set all card error
                                cardNumberError = cardValidator.cardNumberError,
                                cardTypeError = cardValidator.cardTypeError,
                                cardHolderNameError = cardValidator.cardHolderNameError,
                                cardDateError = cardValidator.dateError,
                                cardIssuerNameError = cardValidator.issuerNameError,
                                cardSecurityKeyError = cardValidator.securityKeyError
                            )
                        }
                    }

                }
            }

            CredentialEvent.SavePassword -> {
                newPassword?.let { password ->
                    val passwordValidator = PasswordValidator.validatePassword(password)

                    val errors = listOfNotNull(
                        passwordValidator.securityKeyError,
                        passwordValidator.titleError,
                        passwordValidator.urlError,
                        passwordValidator.userDetailError,
                        passwordValidator.tagsError
                    )

                    if (errors.isEmpty()) {
                        _state.update {
                            it.copy(
                                isAddNewCredentialSheetOpen = false,

                                // Reset all password error
                                passwordTitleError = null,
                                passwordUrlError = null,
                                passwordSecurityKeyError = null,
                                passwordUserDetailError = null,
                                passwordTagsError = null,

                                // Reset all card error
                                cardNumberError = null,
                                cardHolderNameError = null,
                                cardTypeError = null,
                                cardDateError = null,
                                cardIssuerNameError = null,
                                cardSecurityKeyError = null
                            )
                        }

                        println("$TAG Saving Password : $password")

                        screenModelScope.launch {
                            credentialDataSource.addPassword(password)
                            delay(300L)
                            newCard = null
                            newPassword = null
                        }
                    } else {
                        _state.update {
                            it.copy(
                                // Set all card error
                                passwordUrlError = passwordValidator.urlError,
                                passwordTitleError = passwordValidator.titleError,
                                passwordUserDetailError = passwordValidator.userDetailError,
                                passwordSecurityKeyError = passwordValidator.securityKeyError,
                                passwordTagsError = passwordValidator.tagsError
                            )
                        }
                    }
                }
            }

            is CredentialEvent.OnFilterChange -> {
                screenModelScope.launch {
                    _filterTag.value = event.value

                    println("$TAG FilterTag Updated to ${_filterTag.value}")
                }

                _filterTag.update {
                    event.value
                }
                println("$TAG FilterTag Updated to ${_filterTag.value}")

//                _state.update {
//                    it.copy(
//                        filterTag = event.value
//                    )
//                }


            }
        }
    }
}