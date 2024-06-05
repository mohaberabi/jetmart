package com.mohaberabi.jetmart.features.checkout.presentation.viewmodel

import com.mohaberabi.jetmart.core.util.UiText

sealed interface CheckoutEvents {


    data class Error(val error: UiText) : CheckoutEvents
    data class OrderCreated(val id: String) : CheckoutEvents
}