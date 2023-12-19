package com.sandeep03edu.passwordmanager.manager.credentials.domain

import kotlinx.coroutines.flow.Flow

interface CredentialDataSource {
    fun getCards(): Flow<List<Card>>
    fun getPasswords(): Flow<List<Password>>
    fun getFilteredPasswords(filterTag : String): Flow<List<Password>>
    fun getPasswordById(appId : String): Flow<Password>
    fun getCardById(appId : String): Flow<Card>

    fun addCard(card: Card)
    fun addPassword(password: Password)

    fun deleteAllCards()
    fun deleteAllPasswords()
}