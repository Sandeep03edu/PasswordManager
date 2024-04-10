package com.sandeep03edu.passwordmanager.manager.utils.data

import com.sandeep03edu.passwordmanager.SharedRes
import dev.icerock.moko.resources.ImageResource

fun getDarkCardBackgroundList() : MutableList<ImageResource>{
    val list = mutableListOf<ImageResource>()
    list.add(SharedRes.images.card_back1)
    list.add(SharedRes.images.card_back2)
//    list.add(SharedRes.images.card_back3)
    list.add(SharedRes.images.card_back4)
    list.add(SharedRes.images.card_back5)

    return list
}

fun getRandomDarkCardBackground() : ImageResource{
    val darkBackgroundList = getDarkCardBackgroundList()
    val randomNum = (0..<darkBackgroundList.size).random()

    return darkBackgroundList[randomNum]
}