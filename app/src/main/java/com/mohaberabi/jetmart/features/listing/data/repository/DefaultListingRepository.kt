package com.mohaberabi.jetmart.features.listing.data.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.category.domain.source.remote.CategoriesRemoteDataSource
import com.mohaberabi.jetmart.features.listing.domain.model.ListingResponseModel
import com.mohaberabi.jetmart.features.listing.domain.repository.ListingRepository
import com.mohaberabi.jetmart.features.listing.domain.source.ListingRemoteDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DefaultListingRepository(
    private val categoriesRemoteDataSource: CategoriesRemoteDataSource,
    private val itemsListingRemoteDataSource: ListingRemoteDataSource,
    private val dispatchers: DispatchersProvider
) : ListingRepository {
    override suspend fun getListing(
        parentCategoryId: String,
    ): AppResult<ListingResponseModel, DataError> {
        return withContext(dispatchers.io) {
            val categoriesDeffered =
                async { categoriesRemoteDataSource.getCategoriesByParentId(parentCategoryId) }
            val itemsDeffered = async { itemsListingRemoteDataSource.getListing(parentCategoryId) }
            val categoriesRes = categoriesDeffered.await()
            val itemsRes = itemsDeffered.await()
            return@withContext when {
                categoriesRes is AppResult.Error -> categoriesRes
                itemsRes is AppResult.Error -> itemsRes
                categoriesRes is AppResult.Done && itemsRes is AppResult.Done -> {
                    AppResult.Done(
                        ListingResponseModel(
                            childCategories = categoriesRes.data,
                            items = itemsRes.data
                        )
                    )
                }

                else -> AppResult.Error(DataError.Network.UNKNOWN_ERROR)
            }
        }

    }
}