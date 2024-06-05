package com.mohaberabi.jetmart.features.cart.domain.model

import com.mohaberabi.jetmart.features.order.domain.model.OrderItemModel

data class CartItemModel(
    val id: String,
    val qty: Int,
    val price: Double,
    val image: String,
    val name: Map<String, String>,
    val didPriceChange: Boolean = false,
    val didBecameOutStock: Boolean = false
) {
    val totalPrice: Double
        get() = price * qty
}


fun CartItemModel.toOrderItemModel(): OrderItemModel {
    return OrderItemModel(
        id = id,
        qty = qty,
        price = price,
        name = name,
        image = image
    )
}