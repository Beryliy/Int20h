package com.fourcore.presentation.challengeConstructor

import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import com.fourcore.SingleLiveEvent
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.Challenge
import com.fourcore.domain.User
import com.fourcore.presentation.BaseViewModel
import com.fourcore.scheduled.DeadlineCheckerWorker
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class ChallengeConstructorViewModel(
    val challengeRepository: ChallengeRepository,
    val userRepository: UserRepository
) : BaseViewModel() {
    lateinit var presentationChallenge: PresentationChallenge
    lateinit var challengeReceiver: User
    val challengeNotValidEvent = SingleLiveEvent<String>()
    val contactsInitedEvent = SingleLiveEvent<List<User>>()
    val deadlineCalendar = Calendar.getInstance()
    val workerRequestEvent = SingleLiveEvent<OneTimeWorkRequest>()
    fun createChallenge() {
        viewModelScope.launch {
            if (validateChallange(presentationChallenge)) {
                val challenge = Challenge(
                    userRepository.getCurrentUser(),
                    challengeReceiver,
                    presentationChallenge.name,
                    presentationChallenge.description,
                    deadlineCalendar.time,
                    1,
                    4,
                    1
                )
                userRepository.getCurrentUser().apply { ++points }
                userRepository.updateUser(userRepository.getCurrentUser())
                val challengeId: String = challengeRepository.createChallenge(challenge)
                val data = Data.Builder()
                data.putString(DeadlineCheckerWorker.CHALLENGE_ID_KEY, challengeId)
                val deadlineWorkRequest = OneTimeWorkRequestBuilder<DeadlineCheckerWorker>()
                    .setInitialDelay(
                        deadlineCalendar.timeInMillis - Date().time,
                        TimeUnit.MILLISECONDS
                    )
                    .setInputData(data.build())
                    .addTag(DeadlineCheckerWorker::class.java.simpleName)
                    .build()
                workerRequestEvent.postValue(deadlineWorkRequest)
            } else {
                challengeNotValidEvent.postValue("")
            }
        }
    }

    fun initContacts() {
        viewModelScope.launch {
            val contacts = userRepository.getAllUsers()
            contactsInitedEvent.postValue(contacts)
        }
    }

    fun changeDeadlineDate(year: Int, month: Int, day: Int) {
        deadlineCalendar.set(Calendar.YEAR, year)
        deadlineCalendar.set(Calendar.MONTH, month)
        deadlineCalendar.set(Calendar.DAY_OF_MONTH, day)
    }

    fun changeDeadlineTime(hour: Int, minute: Int) {
        deadlineCalendar.set(Calendar.HOUR, hour)
        deadlineCalendar.set(Calendar.MINUTE, minute)
    }

    private fun validateChallange(presentationChallenge: PresentationChallenge): Boolean {
        if (presentationChallenge.name.isBlank()) {
            return false
        }
        return true
    }

}