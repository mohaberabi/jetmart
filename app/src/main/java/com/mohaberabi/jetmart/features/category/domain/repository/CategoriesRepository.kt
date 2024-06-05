package com.mohaberabi.jetmart.features.category.domain.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.category.domain.model.CategoryModel

interface CategoriesRepository {


    suspend fun getCategories(): AppResult<List<CategoryModel>, DataError>

    suspend fun getCategoriesByParentId(parentId: String): AppResult<List<CategoryModel>, DataError>
}