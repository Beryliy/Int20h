package com.fourcore.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fourcore.domain.Challenge
import com.fourcore.domain.ChallengePerform
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T : Any> Query.awaitWithId(parser: (documentSnapshot: DocumentSnapshot, id: String) -> T): MutableList<T> {
    return suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                val list = it.result!!.documents.map { snap -> parser.invoke(snap, snap.id) }.toMutableList()
                continuation.resume(list)
            } else {
                continuation.resumeWithException(it.exception ?: IllegalStateException())
            }
        }
    }
}

suspend fun <T : Any> Query.awaitSingleWithId(parser: (documentSnapshot: DocumentSnapshot, id: String) -> T): T {
    return suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null  && !it.result?.documents.isNullOrEmpty()) {
                val snap = it.result!!.documents[0]
                continuation.resume(parser(snap, snap.id))
            } else {
                continuation.resumeWithException(it.exception ?: IllegalStateException())
            }
        }
    }
}

suspend fun <T : Any> DocumentReference.awaitSingleWithId(parser: (documentSnapshot: DocumentSnapshot, id: String) -> T): T {
    return suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null && it.result!!.exists()) {
                continuation.resume(parser(it.result!!, id))
            } else {
                continuation.resumeWithException(it.exception ?: IllegalStateException())
            }
        }
    }
}

fun Query.addChallengeSnapshotAddedListener(
    parser: (documentSnapshot: DocumentSnapshot, id: String) -> Challenge
): LiveData<List<Challenge>> {
    val liveData = MutableLiveData<List<Challenge>>()
    this.addSnapshotListener { snap, exc ->
        if (exc != null) return@addSnapshotListener
        val posts = ArrayList<Challenge>()
        for (docChange in snap!!.documentChanges) {
            if (docChange.type == DocumentChange.Type.ADDED) {
                posts.add(parser.invoke(docChange.document, docChange.document.id))
            }
        }
        liveData.postValue(posts)
    }
    return liveData
}


fun Query.addChallengePerformSnapshotAddedListener(
    parser: (documentSnapshot: DocumentSnapshot, id: String) -> ChallengePerform
): LiveData<List<ChallengePerform>> {
    val liveData = MutableLiveData<List<ChallengePerform>>()
    this.addSnapshotListener { snap, exc ->
        if (exc != null) return@addSnapshotListener
        val posts = ArrayList<ChallengePerform>()
        for (docChange in snap!!.documentChanges) {
            if (docChange.type == DocumentChange.Type.ADDED) {
                posts.add(parser.invoke(docChange.document, docChange.document.id))
            }
        }
        liveData.postValue(posts)
    }
    return liveData
}


