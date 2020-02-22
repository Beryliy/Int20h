package com.fourcore.presentation.challengeConstructor

import androidx.lifecycle.viewModelScope
import com.fourcore.SingleLiveEvent
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.Challenge
import com.fourcore.presentation.BaseViewModel
import kotlinx.coroutines.launch
import java.util.*

class ChallengeConstructorViewModel(
    val challengeRepository: ChallengeRepository,
    val userRepository: UserRepository
): BaseViewModel() {
    val challengeNotValidEvent = SingleLiveEvent<String>()
    var deadline: Date? = null
    fun createChallenge(
        presentationChallenge: PresentationChallenge
    ) {
//        viewModelScope.launch {
//            if (validateChallange(presentationChallenge)) {
//                val challenge = Challenge(
//                    userRepository.getCurrentUser(),
//                    presentationChallenge.name,
//                    presentationChallenge.description,
//                    1,
//                    4,
//                    1,
//                    presentationChallenge.deadline
//                )
//                challengeRepository.insertChallenge(challenge)
//            } else {
//                challengeNotValidEvent.postValue("")
//            }
//        }
    }

    private fun validateChallange(presentationChallenge: PresentationChallenge): Boolean {
        if(presentationChallenge.name.isBlank()) {
            return false
        }
        return true
    }

}