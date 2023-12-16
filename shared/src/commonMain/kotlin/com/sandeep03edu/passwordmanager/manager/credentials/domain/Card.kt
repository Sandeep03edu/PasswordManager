package com.sandeep03edu.passwordmanager.manager.credentials.domain

import kotlinx.datetime.Clock

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
    var creationTime: Long = Clock.System.now().epochSeconds
)
