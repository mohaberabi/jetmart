package com.mohaberabi.jetmart.features.settings.presentation.viewmodel

sealed interface SettingsAction {


    data object OnSignOutClick : SettingsAction

}