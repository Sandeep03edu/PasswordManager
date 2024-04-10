package com.sandeep03edu.passwordmanager.manager.profile.domain

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class UserState(
    var _id: String="",
    var firstName: String="",
    var lastName: String="",
    var email: String="",
    var loginPin: String="",
    var appPin: String="",
    var token: String="",
) {
    fun toJson() = run { Json.encodeToString(serializer(), this) }

    companion object {
        fun fromJson(json: String) = run { Json.decodeFromString<UserState>(json) }
    }
}

