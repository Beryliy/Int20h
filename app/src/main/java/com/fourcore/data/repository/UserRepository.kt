package com.fourcore.data.repository

import com.fourcore.domain.User

interface UserRepository {
    fun getCurrentUser(): User
}