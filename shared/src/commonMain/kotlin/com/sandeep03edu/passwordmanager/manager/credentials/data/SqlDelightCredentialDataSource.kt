package com.sandeep03edu.passwordmanager.manager.credentials.data

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.sandeep03edu.passwordmanager.database.CredentialDatabase
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.CredentialDataSource
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.credentials.domain.toDecryptedCard
import com.sandeep03edu.passwordmanager.manager.credentials.domain.toDecryptedPassword
import com.sandeep03edu.passwordmanager.manager.credentials.domain.toEncryptedCard
import com.sandeep03edu.passwordmanager.manager.credentials.domain.toEncryptedPassword
import com.sandeep03edu.passwordmanager.manager.credentials.presentation.TAG
import com.sandeep03edu.passwordmanager.manager.utils.data.tagsListToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.JvmOverloads

class SqlDelightCredentialDataSource(
    db: CredentialDatabase,
) : CredentialDataSource {

    private val cardQueries = db.cardQueries;
    private val passwordQueries = db.passwordQueries;

    override fun getCards(): Flow<List<Card>> {
        return cardQueries
            .getAllCards()
            .asFlow()
            .mapToList()
            .map {
                supervisorScope {
                    it
                        .map { cardEntity ->
                            async {
                                cardEntity
                                    .toCard()
                                    .toDecryptedCard()
                            }
                        }
                        .map {
                            it.await()
                        }
                }
            }
    }

    override fun getPasswords(): Flow<List<Password>> {

        return passwordQueries
            .getAllPassword()
            .asFlow()
            .mapToList()
            .map {
                supervisorScope {
                    it.map { passwordEntity ->
                        async {
                            passwordEntity
                                .toPassword()
                                .toDecryptedPassword()
                        }
                    }
                        .map {
                            it.await()
                        }
                }
            }
    }

    override fun getFilteredPasswords(filterTag: String): Flow<List<Password>> {
        return passwordQueries
            .getFilterPassword(filterTag)
            .asFlow()
            .mapToList()
            .map {
                supervisorScope {
                    it.map { passwordEntity ->
                        async {
                            passwordEntity
                                .toPassword()
                                .toDecryptedPassword()
                        }
                    }
                        .map {
                            it.await()
                        }
                }
            }
    }

    override fun getPasswordById(appId: String): Flow<Password> {
        return passwordQueries
            .getPasswordById(appId)
            .asFlow()
            .map {
                supervisorScope {
                    it.executeAsOne()
                        .toPassword()
                        .toDecryptedPassword()
                }
            }
    }

    override fun getCardById(appId: String): Flow<Card> {
        return cardQueries
            .getCardById(appId)
            .asFlow()
            .map {
                supervisorScope {
                    it.executeAsOne()
                        .toCard()
                        .toDecryptedCard()
                }
            }
    }


    override fun addCard(card: Card) {
        val encryptedCard = card.toEncryptedCard()

        cardQueries.insertCard(
            appId = encryptedCard.appId,
            createdBy = encryptedCard.createdBy,
            issuerName = encryptedCard.issuerName,
            cardHolderName = encryptedCard.cardHolderName,
            cardType = encryptedCard.cardType,
            cardNumber = encryptedCard.cardNumber,
            issueDate = encryptedCard.issueDate,
            expiryDate = encryptedCard.expiryDate,
            pin = encryptedCard.pin,
            cvv = encryptedCard.cvv,
            creationTime = encryptedCard.creationTime,
            isSynced = encryptedCard.isSynced,
            updatedAt = encryptedCard.updatedAt
        )
    }

    override fun addPassword(password: Password) {
        val encryptedPassword = password.toEncryptedPassword()
        passwordQueries.insertPassword(
            appId = encryptedPassword.appId,
            createdBy = encryptedPassword.createdBy,
            title = encryptedPassword.title,
            url = encryptedPassword.url,
            username = encryptedPassword.username,
            emailId = encryptedPassword.email,
            password = encryptedPassword.password,
            pin = encryptedPassword.pin,
            tags = tagsListToString(encryptedPassword.tags),
            creationTime = encryptedPassword.creationTime,
            isSynced = encryptedPassword.isSynced,
            updatedAt = encryptedPassword.updatedAt
        )
    }

    override fun deleteAllCards() {
        cardQueries.deleteAllCards()
    }

    override fun deleteAllPasswords() {
        passwordQueries.deleteAllPasswords()
    }

    override fun deleteAllUserCards(userId: String) {
        cardQueries.deleteAllUserCards(userId)
    }

    override fun deleteAllUserPasswords(userId: String) {
        passwordQueries.deleteAllUserPasswords(userId)
    }


    override fun deletePasswordById(appId: String) {
        passwordQueries.deletePasswordById(appId)
    }

    override fun deleteCardById(appId: String) {
        cardQueries.deleteCardById(appId)
    }
}

@JvmOverloads
fun <T : Any> Flow<Query<T>>.mapToList(
    context: CoroutineContext = Dispatchers.Default,
): Flow<List<T>> = map {
    withContext(context) {
        it.executeAsList()
    }
}
