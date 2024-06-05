package com.mohaberabi.jetmart.features.settings.di

import com.mohaberabi.jetmart.features.settings.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val settingsModule = module {
    viewModelOf(::SettingsViewModel)
}