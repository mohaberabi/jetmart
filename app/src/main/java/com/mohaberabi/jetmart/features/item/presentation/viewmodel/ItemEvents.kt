package com.mohaberabi.jetmart.features.item.presentation.viewmodel

import com.mohaberabi.jetmart.core.util.UiText

sealed interface ItemEvents {


    data class Error(val error: UiText) : ItemEvents
    data object GoSignIn : ItemEvents
}