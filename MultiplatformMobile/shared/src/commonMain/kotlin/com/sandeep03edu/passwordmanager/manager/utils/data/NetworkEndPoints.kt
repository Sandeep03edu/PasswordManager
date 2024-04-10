package com.sandeep03edu.passwordmanager.manager.utils.data

class NetworkEndPoints {

    companion object {
        const val loginUser : String = "/api/auth/login"
        const val registerUser : String = "/api/auth/register"
        const val updateUser : String = "/api/auth/update"

        const val restartApi : String = "/api/auth/restart"

        const val addUpdateCard: String =  "/api/credentials/addUpdateCard"
        const val addUpdatePassword: String =  "/api/credentials/addUpdatePassword"
        const val deleteCard: String =  "/api/credentials/deleteCardById"
        const val deletePassword: String =  "/api/credentials/deletePasswordById"
    }
}