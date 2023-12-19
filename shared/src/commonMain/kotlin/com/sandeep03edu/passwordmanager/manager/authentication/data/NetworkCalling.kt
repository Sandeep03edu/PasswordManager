package com.sandeep03edu.passwordmanager.manager.authentication.data

import com.sandeep03edu.passwordmanager.core.data.Background
import com.sandeep03edu.passwordmanager.manager.credentials.domain.Card
import com.sandeep03edu.passwordmanager.manager.profile.domain.AuthResponse
import com.sandeep03edu.passwordmanager.manager.profile.domain.CredentialResponse
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserToken
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val TAG = "NetworkCallingTag"
val BASE_URL = "http://192.168.1.4:5000"

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

fun getCardCredentialResult(
    url: String,
    card: Card = Card(),
    result: (CredentialResponse) -> Unit,
) {
    val client = HttpClient()
    val scope = CoroutineScope(Background)

    scope.launch {
        try {
            val response =
                client.get(urlString = "$BASE_URL${url}")
                {
                    bearerAuth(getLoggedInUserToken())
                    setBody(card.toJson())
                }

            val bodyText: String = response.bodyAsText()
            val jsonResp: CredentialResponse = Json.decodeFromString(bodyText)
            result(jsonResp)
        } catch (err: Exception) {
            result(CredentialResponse(error = err.message!!, success = false))
        }
    }

}