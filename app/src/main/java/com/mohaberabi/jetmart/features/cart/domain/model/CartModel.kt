package com.mohaberabi.jetmart.features.cart.domain.model

data class CartModel(
    val items: Map<String, CartItemModel> = mapOf()
) {
    fun getItem(id: String): CartItemModel? = items[id]
    val cartTotal: Double
        get() = items.values.sumOf { it.totalPrice }
}
