package com.sandeep03edu.passwordmanager.manager.credentials.data

import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.TAG
import com.sandeep03edu.passwordmanager.manager.utils.data.stringTagsToList
import database.CardEntity
import database.PasswordEntity

fun CardEntity.toCard(): Card {
    return Card(
        appId = appId,
        createdBy = createdBy,
        issuerName = issuerName,
        cardHolderName = cardHolderName,
        cardType = cardType,
        cardNumber = cardNumber,
        pin = pin,
        issueDate = issueDate,
        expiryDate = expiryDate,
        cvv = cvv,
        isSynced = isSynced,
        creationTime = creationTime
    )
}

fun PasswordEntity.toPassword(): Password {
    return Password(
        appId = appId,
        createdBy = createdBy,
        title = title,
        url = url,
        username = username,
        email = emailId,
        password = password,
        pin = pin,
        tags = stringTagsToList(tags),
        isSynced = isSynced,
        creationTime = creationTime
    )
}