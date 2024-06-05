package com.mohaberabi.jetmart.features.auth.di

import com.mohaberabi.jetmart.features.auth.data.repository.DefaultUserRepository
import com.mohaberabi.jetmart.features.auth.data.source.local.UserDataStore
import com.mohaberabi.jetmart.features.auth.data.source.remote.source.FirebaseUserRemoteDataSource
import com.mohaberabi.jetmart.features.auth.domain.repository.UserRepository
import com.mohaberabi.jetmart.features.auth.domain.source.local.UserLocalDataSource
import com.mohaberabi.jetmart.features.auth.domain.source.remote.UserRemoteDataSource
import com.mohaberabi.jetmart.features.auth.presentation.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val authModule = module {
    singleOf(::FirebaseUserRemoteDataSource).bind<UserRemoteDataSource>()
    singleOf(::UserDataStore).bind<UserLocalDataSource>()
    singleOf(::DefaultUserRepository).bind<UserRepository>()
    viewModelOf(::AuthViewModel)
}