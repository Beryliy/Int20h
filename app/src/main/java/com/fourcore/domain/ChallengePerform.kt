package com.fourcore.domain

class ChallengePerform {

    var id: String? = null
    lateinit var challenge: Challenge
    var score: Int = 0
    var likeIds: ArrayList<String> = ArrayList()
    lateinit var imageUrl: String


    constructor(
        challenge: Challenge,
        imageUrl: String,
        likedIds: ArrayList<String> = ArrayList(),
        score: Int = 0,
        id: String? = null
    ) {
        this.id = id
        this.challenge = challenge
        this.likeIds = likedIds
        this.score = score
        this.imageUrl = imageUrl

    }

    constructor()
}