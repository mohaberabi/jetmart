package com.mohaberabi.jetmart.features.item.domain.model

data class ItemListingModel(
    val id: String,
    val categoryId: String,
    val itemId: String,
    val image: String,
    val name: Map<String, String>,
    val price: Double,
)

