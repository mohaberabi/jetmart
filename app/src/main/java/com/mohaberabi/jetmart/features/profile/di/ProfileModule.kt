package com.mohaberabi.jetmart.features.profile.di

import com.mohaberabi.jetmart.features.profile.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val profileModule = module {
    viewModelOf(::ProfileViewModel)
}