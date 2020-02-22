package com.fourcore.presentation.received_tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.Challenge
import kotlinx.coroutines.launch

class ReceivedTasksViewModel(
    private val userRepository: UserRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    private val challangesLiveData = MediatorLiveData<List<Challenge>>()

    fun getChallangesLiveData(): LiveData<List<Challenge>> {
        viewModelScope.launch {
            val currentUserId = userRepository.getCurrentUser().id!!
            challangesLiveData.addSource(challengeRepository.getAddedChallengeLiveData(currentUserId)) {
                challangesLiveData.postValue(it)
            }
        }
        return challangesLiveData
    }

    fun performTask(task: Challenge) {

    }

}