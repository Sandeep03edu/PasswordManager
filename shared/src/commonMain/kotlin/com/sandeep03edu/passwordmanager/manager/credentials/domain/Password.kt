package com.sandeep03edu.passwordmanager.manager.credentials.domain

import com.sandeep03edu.passwordmanager.manager.profile.domain.UserState
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Password(
    var appId: String = Clock.System.now().epochSeconds.toString(),
    var _id: String = "",
    var createdBy: String = "",
    var title: String = "",
    var url: String = "",
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var pin: String = "",
    var tags: MutableList<String> = mutableListOf<String>(),
    // 0-> Not Synced , 1 -> Synced, 2-> Syncing
    var isSynced: Long = 0,
    var creationTime: Long = Clock.System.now().epochSeconds,
) {
    fun toJson() = run { Json.encodeToString(serializer(), this) }

    companion object {
        fun fromJson(json: String) = run { Json.decodeFromString<UserState>(json) }
    }
}
