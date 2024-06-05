package com.mohaberabi.jetmart.features.cart.domain.model


data class PendingCartItem(
    val userId: String,
    val item: CartItemModel,
)

data class DeletedCartItem(
    val cartItemId: String,
    val userId: String
)