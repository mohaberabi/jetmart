package com.mohaberabi.jetmart.features.order.domain.model

data class OrderItemModel(
    val id: String,
    val name: Map<String, String>,

    val price: Double,
    val qty: Int,
    val image: String
) {
    val totalPrice: Double
        get() = qty * price
}
