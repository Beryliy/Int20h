package com.fourcore.data.repository

import androidx.lifecycle.LiveData
import com.fourcore.domain.Challenge

interface ChallengeRepository {
    suspend fun createChallenge(challenge: Challenge)
    suspend fun getOwnerChallenges(ownerId: String): List<Challenge>
    suspend fun getReceivedChallenges(participantId: String): List<Challenge>
    suspend fun finishChallenge(challenge: Challenge)
    suspend fun getAddedChallengeLiveData(participantId: String): LiveData<List<Challenge>>
}