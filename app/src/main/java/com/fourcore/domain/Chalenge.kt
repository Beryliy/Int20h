package com.fourcore.domain

data class Chalenge(
    val name: String,
    val creationPrice: Int,
    val award: Int,
    val fine: Int,
    val description: String
) {
}