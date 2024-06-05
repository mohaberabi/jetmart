package com.mohaberabi.jetmart.features.settings.presentation.viewmodel

sealed interface SettingsEvent {

    data object SignedOut : SettingsEvent
}