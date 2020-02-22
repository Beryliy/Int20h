package com.fourcore.presentation.score_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcore.data.repository.UserRepository
import com.fourcore.domain.User
import kotlinx.coroutines.launch

class ScoreListViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()
    val usersLiveData = MutableLiveData<List<User>>()

    fun initScoreList() {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            val users = userRepository.getAllUsers().sortedByDescending { it.points }
            usersLiveData.postValue(users)
            loadingLiveData.postValue(false)
        }
    }
}