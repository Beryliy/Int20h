package com.fourcore.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val userLiveData = MutableLiveData<User>()
    fun updateUser() {
        viewModelScope.launch {
            userLiveData.postValue(userRepository.getCurrentUser())
            userLiveData.postValue(userRepository.updateCurrentUser(userRepository.getCurrentUser().phoneNumber))
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}