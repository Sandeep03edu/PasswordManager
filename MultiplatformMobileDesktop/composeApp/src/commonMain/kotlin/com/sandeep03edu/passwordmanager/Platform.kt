package com.sandeep03edu.passwordmanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform