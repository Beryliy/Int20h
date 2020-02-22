package com.fourcore.domain

class User {
    var name: String = ""
    var points: Int = 0
    var phoneNumber: String = ""
    var id: String? = null

    constructor(name: String, points: Int, phoneNumber: String, id: String? = null) {
        this.name = name
        this.points = points
        this.phoneNumber = phoneNumber
        this.id = id
    }

    constructor()

    override fun toString(): String {
        return "$name, $id"
    }
}