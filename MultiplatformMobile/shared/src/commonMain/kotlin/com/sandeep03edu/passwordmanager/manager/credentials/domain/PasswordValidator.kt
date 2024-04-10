package com.sandeep03edu.passwordmanager.manager.credentials.domain

object PasswordValidator {

    fun validatePassword(password: Password) : PasswordValidationResult{
        var res = PasswordValidationResult()
        if(password.title.isBlank()){
            res = res.copy(titleError = "Title can't be empty!!")
        }

        if(password.url.isBlank()){
            res = res.copy(urlError = "Url can't be empty!!")
        }

        if((password.username.isBlank())){
            if((password.email.isBlank())){
                res = res.copy(userDetailError = "Username or Email id is compulsory!!")
            }
        }

        if((password.pin.isBlank())){
            if((password.password.isBlank())){
                res = res.copy(securityKeyError = "Pin or Password is compulsory!!")
            }
        }

        if(password.tags.isEmpty()){
            res = res.copy(tagsError = "Please select at least 1 Tag")
        }

        return res
    }

    data class PasswordValidationResult(
        val titleError: String? = null,
        val urlError: String? = null,

        // Username or Email
        val userDetailError : String? = null,

        // Password or Pin
        val securityKeyError : String? = null,

        val tagsError : String? = null,
    )
}