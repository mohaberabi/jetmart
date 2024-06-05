package com.mohaberabi.jetmart.features.cart.presentation.viewmodel

import com.mohaberabi.jetmart.features.cart.domain.model.CartModel

data class CartState(


    val cart: CartModel = CartModel()
)
