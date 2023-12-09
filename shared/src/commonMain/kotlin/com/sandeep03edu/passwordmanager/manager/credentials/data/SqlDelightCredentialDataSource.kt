package com.sandeep03edu.passwordmanager.manager.credentials.data

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.sandeep03edu.passwordmanager.database.CredentialDatabase
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.CredentialDataSource
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
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
                                cardEntity.toCard()
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
                            println("$TAG AllPasswordEntity:: $passwordEntity")
                            passwordEntity.toPassword()
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
                            passwordEntity.toPassword()
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
                }
            }
    }


    override suspend fun addCard(card: Card) {
        cardQueries.insertCard(
            appId = card.appId,
            issuerName = card.issuerName,
            cardHolderName = card.cardHolderName,
            cardType = if (card.cardType == null) "" else card.cardType!!,
            cardNumber = card.cardNumber,
            issueDate = if (card.issueDate == null) "" else card.issueDate!!,
            expiryDate = if (card.expiryDate == null) "" else card.expiryDate!!,
            pin = card.pin,
            cvv = card.cvv,
            creationTime = card.creationTime,
            isSynced = if (card.isSynced) 1 else 0
        )
    }

    override suspend fun addPassword(password: Password) {
        passwordQueries.insertPassword(
            appId = password.appId,
            title = password.title,
            url = password.url,
            username = password.username,
            emailId = password.email,
            password = password.password,
            pin = password.pin,
            tags = tagsListToString(password.tags),
            creationTime = password.creationTime,
            isSynced = if (password.isSynced) 1 else 0
        )
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
