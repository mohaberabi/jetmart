package com.mohaberabi.jetmart.features.cart.presentation.viewmodel

import com.mohaberabi.jetmart.core.util.UiText

sealed interface CartEvents {


    data class Error(val error: UiText) : CartEvents
}