package com.mohaberabi.jetmart.features.profile.presentation.viewmodel

sealed interface ProfileActions {


    data object OnSaveClick : ProfileActions

    data class OnNameChanged(val name: String) : ProfileActions


    data class OnLastNameChanged(val lastname: String) : ProfileActions


}