package com.fourcore.domain

data class User(
    val name: String,
    val points: Int,
    val avatarURL: String,
    val createdChallenges: Array<Challenge>,
    val receivedChallanges: Array<Challenge>,
    val contacts: MutableSet<User>
) {
}