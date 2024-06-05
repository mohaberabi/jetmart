package com.mohaberabi.jetmart.features.settings.presentation.viewmodel

import com.mohaberabi.jetmart.features.auth.domain.model.UserModel

data class SettingsState(
    val user: UserModel = UserModel.empty,
    val loading: Boolean = false,
)
