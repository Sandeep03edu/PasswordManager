package com.sandeep03edu.passwordmanager.manager.credentials.domain

import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Card(
    var appId: String = Clock.System.now().epochSeconds.toString(),
    var _id : String ="",
    var createdBy : String ="",
    var issuerName: String = "",
    var cardHolderName: String = "",
    var cardType: String? = null,
    var cardNumber: String = "",
    var cvv: String = "",
    var pin: String = "",
    var issueDate: String? = null,
    var expiryDate: String? = null,
    var isSynced: Boolean = false,
    var creationTime: Long = Clock.System.now().epochSeconds,
)
{
    fun toJson() = run { Json.encodeToString(serializer(), this) }

    companion object {
        fun fromJson(json: String) = run { Json.decodeFromString<UserState>(json) }
    }
}