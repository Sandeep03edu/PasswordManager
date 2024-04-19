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
//        list.add(Color(0xff053e77))
        list.add(Color(0xff1070d0))
        list.add(Color(0x41e3faf6))
        list.add(Color(0x2ce8ffff))
    } else {

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
    Dark blue color in dark theme
    Light blue color in light theme
 */
@Composable
fun getBackgroundColor(
): Color {
    val isDark = isSystemInDarkTheme()
    if (!isDark) {
        return Color(0xffeaf3fd)
    }
    return Color(0xff012142)
}
/*
    Dark blue color in light theme
    Light blue color in dark theme
 */
@Composable
fun getFloatingActionButtonColor(
): Color {
    val isDark = isSystemInDarkTheme()
    if (isDark) {
        return Color(0xffc6dfff)
    }
    return Color(0xff0260bd)
}

@Composable
fun getPasswordTagColor(): Color {
    val isDark = isSystemInDarkTheme()

    if (isDark) {
        return Color(0xffdcf9ff)
    }
    return Color(0xff012142)
}

@Composable
fun getSelectedPasswordTagColor(): Color {
    val isDark = isSystemInDarkTheme()
    if (!isDark) {
        return Color(0xffc9c9c9)
    }

    return Color(0xff0260bd)
}


@Composable
fun getPasswordHalfDisplayBackground() : Color {
    val isDark = isSystemInDarkTheme()
    if (!isDark) {
        return Color(0xff0260bd)
    }

    return Color(0xffc6dfff)
}

@Composable
fun getSettingOptionsBackground() : Color {
    val isDark = isSystemInDarkTheme()
    if (!isDark) {
        return Color(0xff0260bd)
    }

    return Color(0xffc6dfff)
}

@Composable
fun getBottomBarBackground() : Color {
    val isDark = isSystemInDarkTheme()
    if (isDark) {
        return Color(0xff0260bd)
    }

    return Color(0x713c9dff)
}

/*
Pair of UpperHalf and BottomHalf header color
 */
@Composable
fun getDetailedHeaderColor() : Pair<Color, Color> {
    val isDark = isSystemInDarkTheme()
    if (isDark) {
        return Pair(Color(0xff1030a2), Color(0xff0085ff))
    }

    return Pair(Color.Yellow, Color.Green)
}

@Composable
fun getTextEditFieldBackground() : Color {
    val isDark = isSystemInDarkTheme()
    if (!isDark) {
        return Color(0xff0260bd)
    }

    return Color(0xffdfebff)
}
