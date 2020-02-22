package com.fourcore.presentation.challengeConstructor

import com.fourcore.SingleLiveEvent
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.domain.Challenge
import com.fourcore.domain.User
import com.fourcore.presentation.BaseViewModel
import java.util.*

class ChallengeConstructorViewModel(
    val challengeRepository: ChallengeRepository
): BaseViewModel() {
    val challengeNotValidEvent = SingleLiveEvent<String>()

    var deadline: Date? = null
    fun createChallenge(
        presentationChallenge: PresentationChallenge,
        currentUser: User
    ) {
        if(validateChallange(presentationChallenge)){
            val challenge = Challenge(
                currentUser,
                presentationChallenge.name,
                presentationChallenge.description,
                1,
                4,
                1,
                presentationChallenge.deadline
            )
            challengeRepository.insertChallenge(challenge)
        } else {
            challengeNotValidEvent.value = ""
        }
    }

    private fun validateChallange(presentationChallenge: PresentationChallenge): Boolean {
        if(presentationChallenge.name.isNullOrBlank()) {
            return false
        }
        return true
    }

}