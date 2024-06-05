package com.mohaberabi.jetmart.features.checkout.presentation.viewmodel

sealed interface CheckoutActions {


    data object OnConfirmOrder : CheckoutActions
}