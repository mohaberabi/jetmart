package com.mohaberabi.jetmart.features.item.domain.model

import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel


data class ItemModel(
    val id: String,
    val name: Map<String, String>,
    val image: String,
    val description: Map<String, String>,
    val price: Double,
    val categoryId: String,
)

fun ItemModel.toCartItemModel(): CartItemModel {
    return CartItemModel(
        id = id,
        name = name,
        price = price,
        qty = 1,
        image = image
    )
}

