package com.fourcore.domain

import java.util.*

class Challenge {
    lateinit var owner: User
    lateinit var participant: User
    lateinit var name: String
    var description: String? = null
    lateinit var deadline: Date
    var creationAward: Int = 1
    var completeAward: Int = 4
    var fine: Int = 1
    var id: String? = null

    constructor(
        owner: User,
        participant: User,
        name: String,
        description: String?,
        deadline: Date,
        creationAward: Int = 1,
        completeAward: Int = 4,
        fine: Int = 1,
        id: String? = null
    ) {
        this.owner = owner
        this.participant = participant
        this.name = name
        this.description = description
        this.deadline = deadline
        this.completeAward = completeAward
        this.creationAward = creationAward
        this.fine = fine
        this.id = id
    }

    constructor()

    override fun toString(): String {
        return "$name $id"
    }
}