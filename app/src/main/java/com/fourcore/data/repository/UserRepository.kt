package com.fourcore.data.repository

import com.fourcore.domain.User

interface UserRepository {
    suspend fun getCurrentUser(): User
    suspend fun createUser(user: User)
    suspend fun updateCurrentUser(phoneNumber: String): User
    suspend fun getAllUsers(): MutableList<User>
    suspend fun getUserById(id: String): User
    suspend fun getUserByPhone(phoneNumber: String): User
    suspend fun updateUser(user: User)
    suspend fun addUserPoints(user: User, points: Int)
}