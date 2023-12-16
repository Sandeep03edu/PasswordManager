package com.sandeep03edu.passwordmanager.manager.utils.domain

import com.appmattus.crypto.Algorithm
import io.ktor.utils.io.core.toByteArray

@OptIn(ExperimentalStdlibApi::class)
fun encryptString(input: String): String {
    val salt = "ABCDEFGHIJKLMNOP"
    val digest = Algorithm.Blake2b.Keyed(
        key = salt.toByteArray()
    ).createDigest()

    val byteArray = input.toByteArray()
    val hashedBytes = digest.digest(byteArray)
    return hashedBytes.toHexString()
}