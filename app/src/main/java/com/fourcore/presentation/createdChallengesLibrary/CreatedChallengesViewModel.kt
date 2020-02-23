package com.fourcore.presentation.createdChallengesLibrary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.global.util.getReadableDate
import com.fourcore.presentation.BaseViewModel
import kotlinx.coroutines.launch

class CreatedChallengesViewModel(
    val userRepository: UserRepository,
    val challengeRepository: ChallengeRepository
): BaseViewModel() {
    val liveCreatedChalenges = MutableLiveData<MutableList<CreatedChallenge>>()
    fun initChallengesData() {
        viewModelScope.launch {
            val challenges = challengeRepository.getOwnerChallenges(userRepository.getCurrentUser().id!!)
            val createdChallenges = mutableListOf<CreatedChallenge>()
            challenges.forEach {
                createdChallenges += CreatedChallenge(
                    it.name,
                    it.description ?: "",
                    it.deadline.getReadableDate()
                )
            }
            liveCreatedChalenges.postValue(createdChallenges)
        }
    }
}