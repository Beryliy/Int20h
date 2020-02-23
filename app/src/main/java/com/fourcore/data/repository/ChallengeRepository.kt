package com.fourcore.data.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.fourcore.domain.Challenge
import com.fourcore.domain.ChallengePerform

interface ChallengeRepository {
    suspend fun createChallenge(challenge: Challenge)
    suspend fun getOwnerChallenges(ownerId: String): List<Challenge>
    suspend fun getReceivedChallenges(participantId: String): List<Challenge>
    suspend fun finishChallenge(challenge: Challenge)
    suspend fun getAddedChallengeLiveData(participantId: String): LiveData<List<Challenge>>
    suspend fun sendChallengePerorming(challenge: ChallengePerform)
    suspend fun uploadImage(bitmap: Bitmap): String
    suspend fun deleteFromActive(choosedChallenge: Challenge)
    suspend fun getUnVotePerformChallenges(voteUserId: String): List<ChallengePerform>
    suspend fun updateChallengePerform(currentPerform: ChallengePerform)
}