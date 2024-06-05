package com.mohaberabi.jetmart.features.cart.presentation.viewmodel

import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel

sealed interface CartActions {


    data object OnClearCartClicked : CartActions

    data class OnIncrementQty(val item: CartItemModel) : CartActions

    data class OnDecrementQty(val item: CartItemModel) : CartActions

    data object OnConfirmOrder : CartActions
    data object OnBackClick : CartActions
}