package com.sandeep03edu.passwordmanager.manager.utils.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

fun getPasswordTagsWithIcons(): List<Pair<String, ImageVector>> {
    val mutableList: MutableList<Pair<String, ImageVector>> = mutableListOf()
    mutableList.add(Pair("Educational", Icons.Default.Person))
    mutableList.add(Pair("Personal", Icons.Default.Person))
    mutableList.add(Pair("Browser", Icons.Default.Person))
    mutableList.add(Pair("Banking", Icons.Default.Person))
    mutableList.add(Pair("Social Media", Icons.Default.Person))
    mutableList.add(Pair("Work", Icons.Default.Person))
    return mutableList
}

fun getPasswordTagColor(tag: String): Color {
    val list = getPasswordTags()
    if (tag == list.get(0)) return Color(0xffff9900)
    if (tag == list.get(1)) return Color(0xff29a329)
    if (tag == list.get(2)) return Color(0xff3333cc)
    if (tag == list.get(3)) return Color(0xffc6538c)
    if (tag == list.get(4)) return Color(0xff29a329)
    if (tag == list.get(5)) return Color(0xff3333cc)

    return Color(0xffff9900)
}

fun getPasswordTags(): List<String> {
    val list = mutableListOf<String>()
    val listIcons = getPasswordTagsWithIcons()
    listIcons.forEach {
        list.add(it.first)
    }
    return list
}


fun tagsListToString(
    list: MutableList<String>,
): String {
    var res = ""

    list.forEach {
        res += "$it,"
    }

    return res
}

fun stringTagsToList(
    tags: String,
): MutableList<String> {
    val list = mutableListOf<String>()
    val strs = tags.split(",")

    strs.forEach {
        if (it.isNotEmpty()) {
            list.add(it)
        }
    }
    return list
}