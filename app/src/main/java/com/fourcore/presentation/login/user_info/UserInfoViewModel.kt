package com.fourcore.presentation.login.user_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.User
import kotlinx.coroutines.launch


class UserInfoViewModel(private val userRepositoryImpl: UserRepository) : ViewModel() {

    val createUserResultLiveData = MutableLiveData<Boolean>()

    fun createUser(name: String, phone: String) {
        viewModelScope.launch {
            userRepositoryImpl.createUser(
                User(
                    name,
                    20,
                    phone
                )
            )
            createUserResultLiveData.postValue(true)
        }
    }
}