package com.fourcore.data.repository.impl

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.domain.Challenge
import com.fourcore.domain.ChallengePerform
import com.fourcore.extensions.addChallengePerformSnapshotAddedListener
import com.fourcore.extensions.addChallengeSnapshotAddedListener
import com.fourcore.extensions.awaitSingleWithId
import com.fourcore.extensions.awaitWithId
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kiwimob.firestore.coroutines.addAwait
import com.kiwimob.firestore.coroutines.deleteAwait
import com.kiwimob.firestore.coroutines.setAwait
import com.kiwimob.firestore.coroutines.updateAwait
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.ByteArrayOutputStream
import java.lang.IllegalStateException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ChallengeRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ChallengeRepository {
    override suspend fun createChallenge(challenge: Challenge): String {
        return firestore.collection("active_challenges").addAwait(challenge).id
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
        firestore.collection("finished_challenges").document(challenge.id!!).setAwait(challenge)
    }

    override suspend fun getAddedChallengeLiveData(participantId: String): LiveData<List<Challenge>> {
        return firestore.collection("active_challenges").whereEqualTo(FieldPath.of("participant", "id"), participantId)
            .addChallengeSnapshotAddedListener { snap, id ->
                val challenge: Challenge = snap.toObject(Challenge::class.java)!!
                challenge.id = id
                challenge
            }
    }

    override suspend fun sendChallengePerorming(challenge: ChallengePerform) {
        firestore.collection("performing_challenges").addAwait(challenge)
    }

    override suspend fun uploadImage(bitmap: Bitmap): String {
        return suspendCancellableCoroutine { cont ->
            val storageRef = storage.reference
            val imageRef = storageRef.child("tasks/${System.currentTimeMillis()}_photo.jpg")
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val uploadTask = imageRef.putBytes(data)
            uploadTask.continueWithTask {task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        cont.resumeWithException(IllegalStateException())
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cont.resume(task.result.toString())
                } else {
                    cont.resumeWithException(IllegalStateException())
                }
            }
        }
    }

    override suspend fun deleteFromActive(choosedChallenge: Challenge) {
        firestore.collection("active_challenges").document(choosedChallenge.id!!).deleteAwait()
    }

    override suspend fun getUnVotePerformChallenges(): LiveData<List<ChallengePerform>> {
        return firestore.collection("performing_challenges").addChallengePerformSnapshotAddedListener {snap, id ->
            val challenge: ChallengePerform = snap.toObject(ChallengePerform::class.java)!!
            challenge.id = id
            challenge
        }
    }

    override suspend fun updateChallengePerform(currentPerform: ChallengePerform) {
        firestore.collection("performing_challenges").document(currentPerform.id!!).updateAwait(
            mapOf(
                "score" to currentPerform.score,
                "likeIds" to currentPerform.likeIds
            ))
    }

    override suspend fun getActiveChallenge(challengeId: String): Challenge {
        return firestore.collection("active_challenges").document(challengeId).awaitSingleWithId { snap, id ->
            val challenge: Challenge = snap.toObject(Challenge::class.java)!!
            challenge.id = id
            challenge
        }
    }

    override suspend fun getchallengePerformOf(challengeId: String): ChallengePerform {
        return firestore.collection("performing_challenges").whereEqualTo(FieldPath.of("challenge", "id"), challengeId).awaitSingleWithId { snap, id ->
            val challenge: ChallengePerform = snap.toObject(ChallengePerform::class.java)!!
            challenge.id = id
            challenge
        }
    }

    override suspend fun finishChallengePerform(challengePerform: ChallengePerform) {
        firestore.collection("performing_challenges").document(challengePerform.id!!).deleteAwait()
        firestore.collection("finished_challenges").addAwait(challengePerform.challenge)
    }
}