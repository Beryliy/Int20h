package com.fourcore.presentation.challengeConstructor

import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fourcore.SingleLiveEvent
import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.Challenge
import com.fourcore.domain.User
import com.fourcore.presentation.BaseViewModel
import kotlinx.coroutines.launch
import java.util.*

class ChallengeConstructorViewModel(
    val challengeRepository: ChallengeRepository,
    val userRepository: UserRepository
): BaseViewModel() {
    lateinit var presentationChallenge: PresentationChallenge
    lateinit var challengeReceiver: User
    val challengeNotValidEvent = SingleLiveEvent<String>()
    val contactsInitedEvent = SingleLiveEvent<List<User>>()
    val liveCalendar = MutableLiveData<Calendar>().apply {
        value = Calendar.getInstance()
    }
    lateinit var deadline: Date
    fun createChallenge(
    ) {
        viewModelScope.launch {
            if (validateChallange(presentationChallenge)) {
                val challenge = Challenge(
                    userRepository.getCurrentUser(),
                    challengeReceiver,
                    presentationChallenge.name,
                    presentationChallenge.description,
                    deadline,
                    1,
                    4,
                    1
                )
                challengeRepository.createChallenge(challenge)
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
        val newDate = liveCalendar.value?.clone() as Calendar
        newDate.set(Calendar.YEAR, year)
        newDate.set(Calendar.MONTH, month)
        newDate.set(Calendar.DAY_OF_MONTH, day)
        liveCalendar.value = newDate
    }

    fun changeDeadlineTime() {

    }

    private fun validateChallange(presentationChallenge: PresentationChallenge): Boolean {
        if(presentationChallenge.name.isBlank()) {
            return false
        }
        return true
    }

}