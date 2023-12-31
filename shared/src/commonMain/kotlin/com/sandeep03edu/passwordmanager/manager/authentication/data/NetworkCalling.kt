package com.sandeep03edu.passwordmanager.manager.authentication.data

import com.sandeep03edu.passwordmanager.core.data.Background
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Password
import com.sandeep03edu.passwordmanager.manager.di.AppModule
import com.sandeep03edu.passwordmanager.manager.profile.domain.AuthResponse
import com.sandeep03edu.passwordmanager.manager.profile.domain.CredentialResponse
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserToken
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val TAG = "NetworkCallingTag"
//val BASE_URL = "http://192.168.1.4:5000"
val BASE_URL = "https://password-manager-sandeep03edu-backend.onrender.com"

fun getAuthResult(
    url: String,
    userState: UserState = UserState(),
    result: (AuthResponse) -> Unit,
) {
    val client = HttpClient()
    val scope = CoroutineScope(Background)

    scope.launch {
        try {
            val response = client.post(urlString = "${BASE_URL}${url}")
            {
                contentType(ContentType.Application.Json)
                setBody(userState.toJson())
                bearerAuth(getLoggedInUserToken())
            }

            val bodyText: String = response.bodyAsText()
            val jsonResp: AuthResponse = Json.decodeFromString(bodyText)
            result(jsonResp)
        } catch (err: Exception) {
            result(AuthResponse(success = false, error = err.message!!))
            println("$TAG Exception : $err")
        }
    }
}

fun getCredentialGetResult(
    url: String,
    card: Card? = null,
    password: Password? = null,
    result: (CredentialResponse) -> Unit,
) {
    val client = HttpClient()
    val scope = CoroutineScope(Background)

    scope.launch {
        try {
            var body = ""
            if (card != null) {
                body = card.toJson()
            } else if (password != null) {
                body = password.toJson()
            }
            val response =
                client.get(urlString = "$BASE_URL${url}")
                {
                    contentType(ContentType.Application.Json)
                    bearerAuth(getLoggedInUserToken())
                    setBody(body)
                }

            val bodyText: String = response.bodyAsText()
            val jsonResp: CredentialResponse = Json.decodeFromString(bodyText)
            result(jsonResp)
        } catch (err: Exception) {
            result(CredentialResponse(error = err.message!!, success = false))
        }
    }
}

fun getCredentialPostResponse(
    url: String,
    card: Card? = null,
    password: Password? = null,
    result: (CredentialResponse) -> Unit,
) {
    val client = HttpClient()
    val scope = CoroutineScope(Background)

    scope.launch {

        try {
            var body = ""
            if (card != null) {
                body = card.toJson()
            } else if (password != null) {
                body = password.toJson()
            }

            println("$TAG BODY:: $body")
            val response = client.post(urlString = "$BASE_URL${url}")
            {
                contentType(ContentType.Application.Json)
                bearerAuth(getLoggedInUserToken())
                setBody(body)
            }

            val bodyText: String = response.bodyAsText()
            val jsonResp: CredentialResponse = Json.decodeFromString(bodyText)
            result(jsonResp)
        } catch (err: Exception) {
            result(CredentialResponse(error = err.message!!, success = false))
        }
    }
}

fun getCredentialDeleteResponse(
    url: String,
    card: Card? = null,
    password: Password? = null,
    result: (CredentialResponse) -> Unit,
) {
    val client = HttpClient()
    val scope = CoroutineScope(Background)

    scope.launch {

        try {
            var body = ""
            if (card != null) {
                body = card.toJson()
            } else if (password != null) {
                body = password.toJson()
            }

            println("$TAG BODY:: $body")
            val response = client.delete(urlString = "$BASE_URL${url}")
            {
                contentType(ContentType.Application.Json)
                bearerAuth(getLoggedInUserToken())
                setBody(body)
            }

            val bodyText: String = response.bodyAsText()
            val jsonResp: CredentialResponse = Json.decodeFromString(bodyText)
            result(jsonResp)
        } catch (err: Exception) {
            result(CredentialResponse(error = err.message!!, success = false))
        }
    }
}

fun updateServerPasswords(appModule: AppModule) {
    getCredentialGetResult("/api/credentials/fetchAllPasswords",
        result = {
            println("${com.sandeep03edu.passwordmanager.TAG} Cred Passwords Resp:: ${it.passwords}")
            MainScope().launch {
                appModule.credentialDataSource.deleteAllPasswords()

                it.let {
                    it.passwords.let { passwords ->
                        passwords.forEach { password ->
                            appModule.credentialDataSource.addPassword(password)
                        }
                    }
                }
            }
        })
}

fun updateServerCards(appModule: AppModule) {
    getCredentialGetResult("/api/credentials/fetchAllCards",
        result = {
            println("${com.sandeep03edu.passwordmanager.TAG} Cred Resp:: ${it.cards}")
            MainScope().launch {
                appModule.credentialDataSource.deleteAllCards()

                it.let {
                    it.cards.let { cards ->
                        cards.forEach { card ->
                            appModule.credentialDataSource.addCard(card)
                            println("Fetched Card $card")
                        }
                    }
                }

            }

        })
}
