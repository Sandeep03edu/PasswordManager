package com.sandeep03edu.passwordmanager.manager.utils.data

import com.russhwolf.settings.Settings
import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState

fun getLoggedInUser() : UserState?{

    // TODO : Remove this
//    return UserState("Sandeep", "Mishra", "+918178538456","1234", "123456")

    val settings = Settings()

    if(settings.hasKey("UserState")){
        println("getUser Has Key")
        val str = settings.getString("UserState", "")

        if(str.isEmpty()){
            println("getUser Empty Key")
            return null
        }
        val usr : UserState = UserState.fromJson(str)
        println("getUser User: $usr")
        return usr
//        return null
    }
    println("getUser No Key found!!")
    return null
}


fun saveLoggedInUser(userState: UserState){
    // TODO : Uncomment
//    val settings = Settings()
//    settings.putString("UserState", userState.toJson())
}
