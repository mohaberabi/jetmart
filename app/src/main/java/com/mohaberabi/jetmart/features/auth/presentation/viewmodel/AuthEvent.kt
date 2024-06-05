package com.mohaberabi.jetmart.features.auth.presentation.viewmodel

import com.mohaberabi.jetmart.core.util.UiText

sealed interface AuthEvent {


    data class Error(val error: UiText) : AuthEvent
    data object AuthDone : AuthEvent
}