package com.sandeep03edu.passwordmanager.manager.credentials.presentation

import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password

data class CredentialState(
    val cards: List<Card> = emptyList(),
    val passwords: List<Password> = emptyList(),
    val filteredPasswords: List<Password>? = null,
    val filterTag: String = "N/A",
    val isAddNewCredentialSheetOpen: Boolean = false,

    val cardIssuerNameError: String? = null,
    val cardHolderNameError: String? = null,
    val cardNumberError: String? = null,
    val cardTypeError: String? = null,
    val cardDateError: String? = null,
    val cardSecurityKeyError: String? = null,

    val passwordTitleError: String? = null,
    val passwordUrlError: String? = null,
    val passwordUserDetailError: String? = null,
    val passwordSecurityKeyError: String? = null,
    val passwordTagsError: String? = null,
)