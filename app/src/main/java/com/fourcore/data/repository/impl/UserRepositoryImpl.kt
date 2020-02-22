package com.fourcore.data.repository.impl

import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.User
import com.fourcore.extensions.awaitSingleWithId
import com.fourcore.extensions.awaitWithId
import com.google.firebase.firestore.FirebaseFirestore
import com.kiwimob.firestore.coroutines.addAwait
import com.kiwimob.firestore.coroutines.await

class UserRepositoryImpl(private val firestore: FirebaseFirestore) : UserRepository {

    @Volatile
    private lateinit var currentUser: User

    @Synchronized
    override suspend fun getCurrentUser() = currentUser

    override suspend fun updateCurrentUser(phoneNumber: String): User {
        currentUser = firestore.collection("users").whereEqualTo("phoneNumber", phoneNumber).awaitSingleWithId { snap, id ->
            val user = snap.toObject(User::class.java)
            user!!.id = id
            user
        }
        return currentUser
    }

    override suspend fun getAllUsers(): List<User> {
        return firestore.collection("users").awaitWithId { snap, id ->
            val user: User = snap.toObject(User::class.java)!!
            user.id = id
            user
        }
    }

    override suspend fun getUserById(id: String): User {
        return firestore.collection("users").document(id).await(User::class.java)
    }

    override suspend fun getUserByPhone(phoneNumber: String): User {
        return firestore.collection("users").whereEqualTo("phoneNumber", phoneNumber).awaitSingleWithId{ snap, id ->
            val user: User = snap.toObject(User::class.java)!!
            user.id = id
            user
        }
    }


    override suspend fun createUser(user: User) {
        currentUser = user
        firestore.collection("users").addAwait(user)
    }
}