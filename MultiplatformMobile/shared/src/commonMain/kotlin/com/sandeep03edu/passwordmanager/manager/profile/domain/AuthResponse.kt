package com.sandeep03edu.passwordmanager.manager.profile.domain

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    var success: Boolean = false,
    var userExist: Boolean = false,
    var error: String = "",
    var _id: String = "",
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var loginPin: String = "",
    var appPin: String = "",
    var token: String = "",
)

fun AuthResponse.toUserState(): UserState {
    return UserState(
        _id, firstName, lastName, email, loginPin, appPin, token
    )
}