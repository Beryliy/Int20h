package com.fourcore.data.repository

import com.fourcore.domain.Challenge

interface ChallengeRepository {
    fun insertChallenge(challenge: Challenge)
}