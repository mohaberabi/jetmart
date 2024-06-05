package com.mohaberabi.jetmart.features.profile.presentation.viewmodel

import com.mohaberabi.jetmart.core.util.UiText


sealed interface ProfileEvent {

    data class Error(val error: UiText) : ProfileEvent


    data object Updated : ProfileEvent
}