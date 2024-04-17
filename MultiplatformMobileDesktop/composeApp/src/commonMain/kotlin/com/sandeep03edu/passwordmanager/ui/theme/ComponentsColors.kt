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
//        list.add(Color(0xffdedddd))
//        list.add(Color(0x41e3faf6))
//        list.add(Color(0x2ce8ffff))

        list.add(Color(0xffafd3ff))
        list.add(Color(0x41e3faf6))
        list.add(Color(0x2ce8ffff))
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
fun getTextColorInverseNonCompose(isDark: Boolean): Color {
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

fun getTextColorNonCompose(isDark: Boolean): Color {
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
        return Color(0xffc5e1ff)
    }
    return Color(0xff012142)
}

fun getPasswordTagColor(isDark: Boolean): Color {
//    val list = getPasswordTags()
//    if (tag == list.get(0)) return Color(0xffff9900)
//    if (tag == list.get(1)) return Color(0xff29a329)
//    if (tag == list.get(2)) return Color(0xff3333cc)
//    if (tag == list.get(3)) return Color(0xffc6538c)
//    if (tag == list.get(4)) return Color(0xff29a329)
//    if (tag == list.get(5)) return Color(0xff3333cc)

    if (isDark) {
        return Color(0xffb4d3f8)
    }
    return Color(0xff012142)
}