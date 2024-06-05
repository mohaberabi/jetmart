package com.mohaberabi.jetmart.features.listing.domain.source

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.item.domain.model.ItemListingModel
import com.mohaberabi.jetmart.features.listing.domain.model.ListingResponseModel

interface ListingRemoteDataSource {

    suspend fun getListing(parentCategoryId: String): AppResult<List<ItemListingModel>, DataError>
}