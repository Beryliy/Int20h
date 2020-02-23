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

    fun getVoteList(): LiveData<List<ChallengePerform>>{
        viewModelScope.launch {
           val list = challengeRepository.getUnVotePerformChallenges(userRepository.getCurrentUser().id!!)
            challengePerformLiveData.postValue(list)
        }
        return challengePerformLiveData
    }

    fun increaseVote(currentPerform: ChallengePerform) {
        GlobalScope.launch {
            currentPerform.score++
            currentPerform.likeIds.add(userRepository.getCurrentUser().id!!)
            challengeRepository.updateChallengePerform(currentPerform)
        }
    }

    fun decreaseVote(currentPerform: ChallengePerform) {
        GlobalScope.launch {
            currentPerform.score--
            currentPerform.likeIds.add(userRepository.getCurrentUser().id!!)
            challengeRepository.updateChallengePerform(currentPerform)
        }
    }
}