package com.fourcore.domain

import java.util.*

data class Challenge(
    val creator: User,
    val name: String,
    val creationPrice: Int,
    val award: Int,
    val fine: Int,
    val description: String,
    val deadline: Date?
) {
}