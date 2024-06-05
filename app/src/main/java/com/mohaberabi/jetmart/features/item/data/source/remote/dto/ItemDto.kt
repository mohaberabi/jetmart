package com.mohaberabi.jetmart.features.item.data.source.remote.dto

import com.mohaberabi.jetmart.features.item.domain.model.ItemModel

data class ItemDto(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val image: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val price: Double,
    val categoryId: String
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        0.0,
        ""
    )
}


fun ItemDto.toItemModel(): ItemModel {
    return ItemModel(
        id = id,
        name = mapOf("en" to nameEn, "ar" to nameAr),
        description = mapOf("en" to descriptionEn, "ar" to descriptionAr),
        image = image,
        price = price,
        categoryId = categoryId
    )
}