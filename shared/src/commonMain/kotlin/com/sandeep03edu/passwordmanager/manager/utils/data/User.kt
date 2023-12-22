package com.sandeep03edu.passwordmanager.manager.utils.data

import com.russhwolf.settings.Settings
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import com.sandeep03edu.passwordmanager.manager.utils.domain.hashString

fun getLoggedInUser(): UserState? {
    val settings = Settings()
    if (settings.hasKey("UserState")) {
        val str = settings.getString("UserState", "")

        if (str.isEmpty()) {
            return null
        }
        val usr: UserState = UserState.fromJson(str)
        return usr
    }
    println("getUser No Key found!!")
    return null
}

fun getLoggedInUserName(): String{
    val user = getLoggedInUser() ?: return ""
    return user.firstName + " " + user.lastName
}
fun getLoggedInUserEmail(): String{
    val user = getLoggedInUser() ?: return ""
    return user.email
}
fun getLoggedInUserToken(): String {
    val user = getLoggedInUser() ?: return ""
    return user.token
}

fun getLoggedInUserId(): String {
    val user = getLoggedInUser() ?: return ""
    return user._id
}

fun saveLoggedInUser(userState: UserState) {
    val settings = Settings()
    settings.putString("UserState", userState.toJson())
}

fun checkLoginPin(
    loginPin: String,
): Boolean {
    val usr = getLoggedInUser()
    if (usr != null) {
        return usr.loginPin == hashString(loginPin)
    }
    return false
}

fun checkAppPin(
    appPin: String,
): Boolean {
    val usr = getLoggedInUser()
    if (usr != null) {
        return usr.appPin == hashString(appPin)
    }
    return false
}