package com.sandeep03edu.passwordmanager

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val age: Int
)