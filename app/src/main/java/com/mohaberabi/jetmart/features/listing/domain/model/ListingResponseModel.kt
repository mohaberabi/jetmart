package com.mohaberabi.jetmart.features.listing.domain.model

import com.mohaberabi.jetmart.features.category.domain.model.CategoryModel
import com.mohaberabi.jetmart.features.item.domain.model.ItemListingModel
import kotlinx.serialization.Serializable

data class ListingResponseModel(
    val childCategories: List<CategoryModel>,
    val items: List<ItemListingModel>
)

@Serializable
data class ListingCategoryModel(
    val id: String,
    val name: Map<String, String>,
)

