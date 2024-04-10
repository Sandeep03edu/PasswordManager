package com.sandeep03edu.passwordmanager.manager.utils.data

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Clock
import kotlin.random.Random

val mRandom: Random = Random(Clock.System.now().epochSeconds)

fun generateRandomLightColor(): Color {
    val red =  ((mRandom.nextInt(90))) + 150
    val green = ((mRandom.nextInt(90))) + 150
    val blue = ((mRandom.nextInt(90))) + 150

    return Color(red, green, blue)
}

fun generateRandomDarkColor(): Color {
    val red =  ((mRandom.nextInt(100))) + 130
    val green = ((mRandom.nextInt(100))) + 150
    val blue = ((mRandom.nextInt(100))) + 140

    return Color(red, green, blue)
}

