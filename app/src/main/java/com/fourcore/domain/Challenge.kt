package com.fourcore.domain

import java.util.*

data class Challenge(
    val owner: User,
    val participant: User,
    val name: String,
    val description: String?,
    val deadline: Date?,
    val creationAward: Int = 1,
    val completeAward: Int = 4,
    val fine: Int = 1

)