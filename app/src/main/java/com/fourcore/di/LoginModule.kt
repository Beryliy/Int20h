package com.fourcore.di

import com.fourcore.login.user_info.UserInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    viewModel { UserInfoViewModel() }
}