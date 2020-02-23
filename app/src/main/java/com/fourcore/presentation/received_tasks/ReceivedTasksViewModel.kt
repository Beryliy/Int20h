package com.fourcore.presentation.received_tasks

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.Challenge
import com.fourcore.domain.ChallengePerform
import kotlinx.coroutines.launch

class ReceivedTasksViewModel(
    private val userRepository: UserRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    private val challangesLiveData = MediatorLiveData<List<Challenge>>()
    val loadingLiveData = MutableLiveData<Boolean>()


    fun getChallangesLiveData(): LiveData<List<Challenge>> {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            val currentUserId = userRepository.getCurrentUser().id!!
            challangesLiveData.addSource(challengeRepository.getAddedChallengeLiveData(currentUserId)) {
                challangesLiveData.postValue(it)
                loadingLiveData.postValue(false)
            }
        }
        return challangesLiveData
    }

    fun createChallengePerform(choosedChallenge: Challenge, photo: Bitmap) {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            val photoUri = challengeRepository.uploadImage(photo)
            challengeRepository.deleteFromActive(choosedChallenge)
            val challengePerform = ChallengePerform(
                choosedChallenge,
                photoUri
            )
            challengeRepository.sendChallengePerorming(challengePerform)
            loadingLiveData.postValue(false)
        }
    }
}