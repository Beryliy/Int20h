package com.fourcore.presentation.vote_challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.ChallengePerform
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VoteTaskViewModel(
    private val userRepository: UserRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    private val challengePerformLiveData = MutableLiveData<List<ChallengePerform>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getVoteList(): LiveData<List<ChallengePerform>> {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            val list = challengeRepository.getUnVotePerformChallenges(userRepository.getCurrentUser().id!!)
            challengePerformLiveData.postValue(list)
            loadingLiveData.postValue(false)

        }
        return challengePerformLiveData
    }

    fun increaseVote(currentPerform: ChallengePerform) {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            currentPerform.score++
            currentPerform.likeIds.add(userRepository.getCurrentUser().id!!)
            challengeRepository.updateChallengePerform(currentPerform)
            loadingLiveData.postValue(false)
        }
    }

    fun decreaseVote(currentPerform: ChallengePerform) {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            currentPerform.score--
            currentPerform.likeIds.add(userRepository.getCurrentUser().id!!)
            challengeRepository.updateChallengePerform(currentPerform)
            loadingLiveData.postValue(false)
        }
    }
}