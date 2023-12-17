package com.sandeep03edu.passwordmanager.manager.utils.domain

import com.appmattus.crypto.Algorithm
import com.sandeep03edu.passwordmanager.core.data.crypto.AES
import com.sandeep03edu.passwordmanager.core.data.crypto.CipherPadding
import com.sandeep03edu.passwordmanager.manager.authentication.presentation.TAG
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
fun encryptString(inputText: String) {
    val key = "byz9VFNtbRQM0yBODcCb1lrUtVVH3D3x"
    val iv = "X05IGQ5qdBnIqAWD"

    val res = AES.encryptAesCbc(inputText.toByteArray(), key.toByteArray(), iv.toByteArray(), CipherPadding.PKCS7Padding)

    println("$TAG Res:: ${res.contentToString()}")
    println("$TAG Res Hex:: ${res.toHexString()}")
}