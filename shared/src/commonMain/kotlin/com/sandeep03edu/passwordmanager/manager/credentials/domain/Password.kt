package com.sandeep03edu.passwordmanager.manager.credentials.domain

import kotlinx.datetime.Clock

data class Password(
    var appId : String = Clock.System.now().epochSeconds.toString(),
    var title:String = "",
    var url : String = "",
    var username : String = "",
    var email : String = "",
    var password : String = "",
    var pin : String = "",
    var tags : MutableList<String> = mutableListOf<String>(),
    var isSynced: Boolean =false,
    var creationTime: Long = Clock.System.now().epochSeconds
)
