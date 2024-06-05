package com.mohaberabi.jetmart.features.cart.data.source.remote.dto

import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel

data class CartItemDto(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val image: String,
    val qty: Int,
    val price: Double,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        0,
        0.0
    )
}


fun CartItemDto.toCartItemModel(): CartItemModel {
    return CartItemModel(
        id = id,
        name = mapOf("ar" to nameAr, "en" to nameEn),
        image = image,
        qty = qty,
        price = price
    )
}

fun CartItemModel.toCartDto(): CartItemDto {
    return CartItemDto(
        id = id,
        nameAr = name.getOrDefault("ar", ""),
        nameEn = name.getOrDefault("en", ""),
        image = image,
        qty = qty,
        price = price
    )
}