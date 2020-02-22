package com.fourcore.login.user_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInfoViewModel : ViewModel() {

    val createUserResultLiveData = MutableLiveData<Boolean>()

    fun createUser(name: String, phone: String) {
        //TODO create user in local db
        createUserResultLiveData.postValue(true)
    }
}