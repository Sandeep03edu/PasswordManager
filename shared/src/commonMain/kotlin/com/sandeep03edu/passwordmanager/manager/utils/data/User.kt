package com.sandeep03edu.passwordmanager.manager.utils.data

import com.russhwolf.settings.Settings
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState

fun getLoggedInUser() : UserState?{
//    return UserState("Sandeep", "Mishra", "+918178538456","1234", "123456")

    val settings = Settings()

    if(settings.hasKey("UserState")){
        val str = settings.getString("UserState", "")

        if(str.isEmpty()){
            return null
        }
        val usr : UserState = UserState.fromJson(str)
        return usr
    }
    println("getUser No Key found!!")
    return null
}


fun saveLoggedInUser(userState: UserState){
    val settings = Settings()
    settings.putString("UserState", userState.toJson())
}
