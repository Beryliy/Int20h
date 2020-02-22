package com.fourcore.domain

import java.util.*

data class Challenge(
    val creator: User,
    val name: String,
    val description: String?,
    val creationAward: Int,
    val completeAward: Int,
    val fine: Int,
    val deadline: Date?
) {
}