package com.sandeep03edu.passwordmanager.manager.utils.data

import com.sandeep03edu.passwordmanager.SharedRes
import dev.icerock.moko.resources.ImageResource

fun getCardIssuersList(): List<String> {
    val list = mutableListOf<String>()

    list.add("Alipay")
    list.add("Amex")
    list.add("Diners")
    list.add("Discover")
    list.add("Elo")
    list.add("Hiper")
    list.add("Hiper Card")
    list.add("JCB")
    list.add("Maestro")
    list.add("Master Card")
    list.add("Mir")
    list.add("Paypal")
    list.add("Union Pay")
    list.add("Visa")

    return list
}

fun getCardLogo(
    cardName: String,
): ImageResource {
    if (cardName.equals("Alipay", true)) {
        return SharedRes.images.alipay
    }
    if (cardName.equals("Amex", true)) {
        return SharedRes.images.amex
    }
    if (cardName.equals("Diners", true)) {
        return SharedRes.images.diners
    }
    if (cardName.equals("Discover", true)) {
        return SharedRes.images.discover
    }
    if (cardName.equals("Elo", true)) {
        return SharedRes.images.elo
    }
    if (cardName.equals("Hiper", true)) {
        return SharedRes.images.hiper
    }
    if (cardName.equals("Hiper Card", true)) {
        return SharedRes.images.hipercard
    }
    if (cardName.equals("JCB", true)) {
        return SharedRes.images.jcb
    }
    if (cardName.equals("Maestro", true)) {
        return SharedRes.images.maestro
    }
    if (cardName.equals("Master Card", true)) {
        return SharedRes.images.mastercard
    }
    if (cardName.equals("Mir", true)) {
        return SharedRes.images.mir
    }
    if (cardName.equals("Paypal", true)) {
        return SharedRes.images.paypal
    }
    if (cardName.equals("Union Pay", true)) {
        return SharedRes.images.unionpay
    }
    if (cardName.equals("Visa", true)) {
        return SharedRes.images.visa
    }


    // TODO : Display App icon
    return SharedRes.images.visa
}