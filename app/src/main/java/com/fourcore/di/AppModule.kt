package com.fourcore.di

import com.fourcore.data.repository.ChallengeRepository
import com.fourcore.data.repository.UserRepository
import com.fourcore.data.repository.impl.ChallengeRepositoryImpl
import com.fourcore.data.repository.impl.UserRepositoryImpl
import com.fourcore.presentation.challengeConstructor.ChallengeConstructorViewModel
import com.fourcore.presentation.profile.ProfileViewModel
import com.fourcore.presentation.score_list.ScoreListViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(FirebaseFirestore.getInstance()) }
    single<ChallengeRepository> { ChallengeRepositoryImpl(FirebaseFirestore.getInstance()) }
    viewModel { ChallengeConstructorViewModel(get(), get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { ScoreListViewModel(get()) }
}