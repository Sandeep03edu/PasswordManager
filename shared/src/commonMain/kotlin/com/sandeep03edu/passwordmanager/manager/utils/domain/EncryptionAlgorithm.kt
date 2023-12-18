package com.sandeep03edu.passwordmanager.manager.utils.domain

import com.appmattus.crypto.Algorithm
import com.sandeep03edu.passwordmanager.core.data.crypto.AES
import com.sandeep03edu.passwordmanager.core.data.crypto.CipherPadding
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.TAG
import com.sandeep03edu.passwordmanager.manager.utils.data.getLoggedInUserId
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray

@OptIn(ExperimentalStdlibApi::class)
fun hashString(input: String): String {
    val salt = "ABCDEFGHIJKLMNOP"
    val digest = Algorithm.Blake2b.Keyed(
        key = salt.toByteArray()
    ).createDigest()

    val byteArray = input.toByteArray()
    val hashedBytes = digest.digest(byteArray)
    return hashedBytes.toHexString()
}

@OptIn(ExperimentalStdlibApi::class)
fun encryptString(inputText: String, appId: String): String {
    val userId = getLoggedInUserId()
    val salt = "ABCDEFGHIJ"

    var merger = salt + appId + userId
    while (merger.length<33){
        merger += appId + userId
    }

    val key = merger.substring(IntRange(start = 0, endInclusive = 31))
    val iv = merger.substring(IntRange(start = 0, endInclusive = 15))

    val res = AES.encryptAesCbc(inputText.toByteArray(), key.toByteArray(), iv.toByteArray(), CipherPadding.PKCS7Padding)

    println("$TAG Res Hex:: ${res.toHexString()}")

    return res.toHexString()
}

@OptIn(ExperimentalStdlibApi::class)
fun decryptString(inputText: String, appId: String) : String{

    val userId = getLoggedInUserId()
    val salt = "ABCDEFGHIJ"

    var merger = salt + appId + userId
    while (merger.length<33){
        merger += appId + userId
    }

    val key = merger.substring(IntRange(start = 0, endInclusive = 31))
    val iv = merger.substring(IntRange(start = 0, endInclusive = 15))

    val res = AES.decryptAesCbc(inputText.decodeHexToByteArray(), key.toByteArray(), iv.toByteArray(), CipherPadding.PKCS7Padding)
    return res.decodeToString()
}

fun String.decodeHexToByteArray(): ByteArray {
    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}