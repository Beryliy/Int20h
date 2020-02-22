package com.fourcore.data.repository.impl

import androidx.lifecycle.LiveData
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.domain.Challenge
import com.fourcore.extensions.addChallengeSnapshotAddedListener
import com.fourcore.extensions.awaitWithId
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.kiwimob.firestore.coroutines.addAwait
import com.kiwimob.firestore.coroutines.deleteAwait

class ChallengeRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ChallengeRepository {
    override suspend fun createChallenge(challenge: Challenge) {
        firestore.collection("active_challenges").addAwait(challenge)
    }

    override suspend fun getOwnerChallenges(ownerId: String): List<Challenge> {
        return firestore.collection("active_challenges")
            .whereEqualTo(FieldPath.of("owner", "id"), ownerId)
            .awaitWithId { snap, id ->
                val challenge: Challenge = snap.toObject(Challenge::class.java)!!
                challenge.id = id
                challenge
            }
    }

    override suspend fun getReceivedChallenges(participantId: String): List<Challenge> {
        return firestore.collection("active_challenges")
            .whereEqualTo(FieldPath.of("participant", "id"), participantId)
            .awaitWithId { snap, id ->
                val challenge: Challenge = snap.toObject(Challenge::class.java)!!
                challenge.id = id
                challenge
            }
    }

    override suspend fun finishChallenge(challenge: Challenge) {
        firestore.collection("active_challenges").document(challenge.id!!).deleteAwait()
        firestore.collection("finished_challenges").addAwait(challenge)
    }

    override suspend fun getAddedChallengeLiveData(participantId: String): LiveData<List<Challenge>> {
        return firestore.collection("active_challenges").whereEqualTo(FieldPath.of("participant", "id"), participantId)
            .addChallengeSnapshotAddedListener { snap, id ->
                val challenge: Challenge = snap.toObject(Challenge::class.java)!!
                challenge.id = id
                challenge
            }
    }
}