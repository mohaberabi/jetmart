package com.mohaberabi.jetmart.features.listing.data.source.remote.dto

import com.mohaberabi.jetmart.features.item.domain.model.ItemListingModel

data class ItemListingDto(
    val id: String,
    val categoryId: String,
    val itemId: String,
    val image: String,
    val nameAr: String,
    val nameEn: String,
    val price: Double,
) {
    constructor() : this(
        "", "", "", "", "", "",
        0.0
    )
}


fun ItemListingDto.toItemListingModel(): ItemListingModel {
    return ItemListingModel(
        id = id,
        name = mapOf("en" to nameEn, "ar" to nameAr),
        image = image,
        price = price,
        categoryId = categoryId,
        itemId = itemId
    )
}
