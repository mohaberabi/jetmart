package com.mohaberabi.jetmart.features.category.domain.model

import com.mohaberabi.jetmart.features.listing.domain.model.ListingCategoryModel

data class CategoryModel(
    val id: String,
    val name: Map<String, String>,
    val image: String,
    val parentId: String
)

fun CategoryModel.toListingCategoryModel(): ListingCategoryModel {
    return ListingCategoryModel(
        id = id,
        name = name
    )
}

fun List<CategoryModel>.mapToListingCategoryModel():
        List<ListingCategoryModel> =
    map { it.toListingCategoryModel() }