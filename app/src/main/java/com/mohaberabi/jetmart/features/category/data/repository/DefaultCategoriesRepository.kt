package com.mohaberabi.jetmart.features.category.data.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.category.domain.model.CategoryModel
import com.mohaberabi.jetmart.features.category.domain.repository.CategoriesRepository
import com.mohaberabi.jetmart.features.category.domain.source.remote.CategoriesRemoteDataSource

class DefaultCategoriesRepository(
    private val categoriesRemoteDataSource: CategoriesRemoteDataSource,
) : CategoriesRepository {
    override suspend fun getCategories(): AppResult<List<CategoryModel>, DataError> =
        categoriesRemoteDataSource.getCategories()

    override suspend fun getCategoriesByParentId(parentId: String): AppResult<List<CategoryModel>, DataError> =
        categoriesRemoteDataSource.getCategoriesByParentId(parentId)

}