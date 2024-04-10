package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password

sealed interface CredentialEvent {
    data class OnDisplayAddEditNewDataClick(val password: Password?, val card: Card?) : CredentialEvent
    object OnDismissAddEditNewDataClick : CredentialEvent

    data class OnCardPasswordOptionSelected(val value: String) : CredentialEvent
    data class OnCardIssuerNameChanged(val value: String) : CredentialEvent
    data class OnCardHolderNameChanged(val value: String) : CredentialEvent
    data class OnCardTypeChanged(val value: String) : CredentialEvent
    data class OnCardNumberChanged(val value: String) : CredentialEvent
    data class OnCardPinChanged(val value: String) : CredentialEvent
    data class OnCardIssueDateChanged(val value: String) : CredentialEvent
    data class OnCardExpiryDateChanged(val value: String) : CredentialEvent
    data class OnCardCvvChanged(val value: String) : CredentialEvent

    data class OnPasswordTitleChange(val value: String) : CredentialEvent
    data class OnPasswordUrlChange(val value: String) : CredentialEvent
    data class OnPasswordUsernameChange(val value: String) : CredentialEvent
    data class OnPasswordEmailIdChange(val value: String) : CredentialEvent
    data class OnPasswordPasswordChange(val value: String) : CredentialEvent
    data class OnPasswordPinChange(val value: String) : CredentialEvent
    data class OnPasswordTagChange(val value: String) : CredentialEvent


    data class OnFilterChange(val value: String) : CredentialEvent


    object SaveCard : CredentialEvent
    object SavePassword : CredentialEvent
}