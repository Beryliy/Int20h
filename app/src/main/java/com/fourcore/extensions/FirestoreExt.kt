package com.fourcore.extensions

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T : Any> Query.awaitWithId(parser: (documentSnapshot: DocumentSnapshot, id: String) -> T): List<T> {
    return suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                val list = it.result!!.documents.map { snap -> parser.invoke(snap, snap.id) }
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

suspend fun <T : Any> DocumentReference.awaitWithId(parser: (documentSnapshot: DocumentSnapshot, id: String) -> T): T {
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

