package com.fourcore.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.Challenge
import com.fourcore.domain.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val challengeRepository: ChallengeRepository
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
//        viewModelScope.launch {
//            challengeRepository.createChallenge(
//                Challenge(
//                    userRepository.getCurrentUser(),
//                    User(
//                        "test",
//                        23,
//                        "sadsad",
//                        "Aazr4tBSQGjTz4xG96Jd"
//                    ),
//                    "name",
//                    "desc",
//                    Date()
//                )
//            )
//        }
    }
}