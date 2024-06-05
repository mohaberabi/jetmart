package com.mohaberabi.jetmart.features.order.data.source.remote.dto

import com.mohaberabi.jetmart.features.order.domain.model.OrderItemModel

data class OrderItemModelDto(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val price: Double,
    val qty: Int,
    val image: String
) {

    constructor() : this(
        "",
        "",
        "",
        0.0,
        1,
        ""
    )

    val totalPrice: Double
        get() = qty * price
}

fun OrderItemModel.toOrderItemModelDto(): OrderItemModelDto {
    return OrderItemModelDto(
        id = id,
        nameEn = name.getOrDefault("en", ""),
        nameAr = name.getOrDefault("ar", ""),
        price = price,
        qty = qty,
        image = image
    )
}

fun OrderItemModelDto.toOrderItemModel(): OrderItemModel {
    return OrderItemModel(
        id = id,
        name = mapOf("en" to nameEn, "ar" to nameAr),
        price = price,
        qty = qty,
        image = image
    )
}