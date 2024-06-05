package com.mohaberabi.jetmart.features.category.data.source.remote.dto

import com.mohaberabi.jetmart.features.category.domain.model.CategoryModel


data class CategoryDto(
    val id: String,
    val parentId: String,
    val nameAr: String,
    val nameEn: String,
    val image: String
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        ""
    )
}


fun CategoryDto.toCategoryModel(): CategoryModel {
    return CategoryModel(
        id = id,
        name = mapOf("ar" to nameAr, "en" to nameEn),
        parentId = parentId,
        image = image
    )
}