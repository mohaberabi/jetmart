package com.mohaberabi.jetmart.features.listing.domain.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.listing.domain.model.ListingResponseModel

interface ListingRepository {

    suspend fun getListing(parentCategoryId: String): AppResult<ListingResponseModel, DataError>


}