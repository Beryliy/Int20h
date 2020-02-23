package com.fourcore.global.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.getReadableDate(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
    return sdf.format(this)
}