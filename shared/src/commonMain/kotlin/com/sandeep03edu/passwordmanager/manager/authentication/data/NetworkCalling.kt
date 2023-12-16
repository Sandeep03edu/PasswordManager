package com.sandeep03edu.passwordmanager.manager.authentication.data

import com.sandeep03edu.passwordmanager.core.data.Background
import com.sandeep03edu.passwordmanager.manager.profile.domain.AuthResponse
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val TAG = "NetworkCallingTag"
val BASE_URL = "http://192.168.1.6:5000"

fun getAuthResult(
    url: String,
    userState: UserState = UserState(),
    result: (AuthResponse?) -> Unit,
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
        } catch (err : Exception) {
            result(null)
            println("$TAG Exception : $err")
        }
    }
}