package com.fourcore.di

import com.fourcore.presentation.challengeConstructor.ChallengeConstructorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    viewModel { ChallengeConstructorViewModel() }
}