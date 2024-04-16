package com.sandeep03edu.passwordmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getCardColorShades(
): List<Color> {
    val isDark = isSystemInDarkTheme()
    val list = mutableListOf<Color>()

    if (!isDark) {
        Color(0xff000000)
        list.add(Color(0xff131c1f))
        list.add(Color(0x51000000))
        list.add(Color(0x86000000))
    } else {
        list.add(Color(0xffefefef))
        list.add(Color(0x41d5fdf7))
        list.add(Color(0x34ddfcf7))
    }
    return list
}

/*
    Black color in dark theme
    White color in light theme
 */
@Composable
fun getTextColorInverse(
): Color {
    val isDark = isSystemInDarkTheme()
    if (!isDark) {
        return Color(0xffffffff)
    }
    return Color(0xff000000)
}

/*
    Black color in light theme
    White color in dark theme
 */
@Composable
fun getTextColor(
): Color {
    val isDark = isSystemInDarkTheme()
    if (isDark) {
        return Color(0xffffffff)
    }
    return Color(0xff000000)
}


/*
    Black color in dark theme
    White color in light theme
 */
@Composable
fun getBackgroundColor(
): Color {
    val isDark = isSystemInDarkTheme()
    if (!isDark) {
        return Color(0xffffffff)
    }
    return Color(0xff000000)
}