package com.sandeep03edu.passwordmanager.manager.credentials.domain

import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import com.sandeep03edu.passwordmanager.manager.utils.domain.decryptString
import com.sandeep03edu.passwordmanager.manager.utils.domain.encryptString
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Card(
    var appId: String = Clock.System.now().epochSeconds.toString(),
    var _id: String = "",
    var createdBy: String = "",
    var issuerName: String = "",
    var cardHolderName: String = "",
    var cardType: String = "",
    var cardNumber: String = "",
    var cvv: String = "",
    var pin: String = "",
    var issueDate: String = "",
    var expiryDate: String = "",
    // 0-> Not Synced , 1 -> Synced, 2-> Syncing
    var isSynced: Long = 0,
    var creationTime: Long = Clock.System.now().epochSeconds,
    var updatedAt: Long = 0,
) {
    fun toJson() = run { Json.encodeToString(serializer(), this) }

    companion object {
        fun fromJson(json: String) = run { Json.decodeFromString<UserState>(json) }
    }
}

fun Card.toEncryptedCard(): Card {
    return Card(
        appId,
        _id,
        createdBy,
        issuerName,
        cardHolderName,
        cardType,
        encryptString(cardNumber, appId),
        encryptString(cvv, appId),
        encryptString(pin, appId),
        encryptString(issueDate, appId),
        encryptString(expiryDate, appId)
    )
}
fun Card.toDecryptedCard(): Card {
    return Card(
        appId,
        _id,
        createdBy,
        issuerName,
        cardHolderName,
        cardType,
        decryptString(cardNumber, appId),
        decryptString(cvv, appId),
        decryptString(pin, appId),
        decryptString(issueDate, appId),
        decryptString(expiryDate, appId)
    )
}