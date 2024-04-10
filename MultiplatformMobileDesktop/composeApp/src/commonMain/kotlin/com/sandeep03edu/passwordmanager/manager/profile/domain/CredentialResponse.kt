package com.sandeep03edu.passwordmanager.manager.profile.domain

import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import kotlinx.serialization.Serializable

@Serializable
data class CredentialResponse(
    val success: Boolean = false,
    val error: String = "",
    val cards: MutableList<Card> = mutableListOf(),
    val passwords: MutableList<Password> = mutableListOf(),
    )

