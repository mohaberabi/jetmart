package com.mohaberabi.jetmart.features.profile.presentation.viewmodel

import com.mohaberabi.jetmart.features.auth.domain.model.UserModel


data class ProfileState(

    val user: UserModel = UserModel.empty,
    val loading: Boolean = false
) {
    val canUpdate
        get() = user.name.isNotEmpty() && user.lastname.isNotEmpty()
}