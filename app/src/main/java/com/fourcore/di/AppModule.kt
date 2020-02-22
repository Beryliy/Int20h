package com.fourcore.di

import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.presentation.challengeConstructor.ChallengeConstructorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single<UserRepository> { }
    single<ChallengeRepository> {}
    viewModel { ChallengeConstructorViewModel() }
}