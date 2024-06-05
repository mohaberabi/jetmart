package com.mohaberabi.jetmart.features.item.presentation.viewmodel

import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import com.mohaberabi.jetmart.features.cart.domain.model.CartModel
import com.mohaberabi.jetmart.features.item.domain.model.ItemModel

data class ItemState(
    val loading: Boolean = false,
    val item: ItemModel? = null,
    val cartItem: CartItemModel? = null,
    val loadingCart: Boolean = false,
    val cartSize: Int = 0
)
