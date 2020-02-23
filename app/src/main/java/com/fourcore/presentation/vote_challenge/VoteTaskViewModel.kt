package com.fourcore.presentation.vote_challenge

import androidx.lifecycle.*
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.ChallengePerform
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VoteTaskViewModel(
    private val userRepository: UserRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    private val challengePerformLiveData = MediatorLiveData<List<ChallengePerform>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getVoteList(): LiveData<List<ChallengePerform>> {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            val voteUserId = userRepository.getCurrentUser().id!!
            challengePerformLiveData.addSource(challengeRepository.getUnVotePerformChallenges()) {list ->
                challengePerformLiveData.postValue(list.filter {!it.likeIds.contains(voteUserId) })
            }
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