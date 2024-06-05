package com.mohaberabi.jetmart.features.listing.data.source.remote

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.mohaberabi.jetmart.core.util.const.CommonParams
import com.mohaberabi.jetmart.core.util.const.EndPoints
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.core.util.error.fromFirebaseFirestoreException
import com.mohaberabi.jetmart.features.item.domain.model.ItemListingModel
import com.mohaberabi.jetmart.features.listing.data.source.remote.dto.ItemListingDto
import com.mohaberabi.jetmart.features.listing.data.source.remote.dto.toItemListingModel
import com.mohaberabi.jetmart.features.listing.domain.source.ListingRemoteDataSource
import kotlinx.coroutines.tasks.await

class FirebaseListingRemoteDataSource(
    private val firestore: FirebaseFirestore,
) : ListingRemoteDataSource {
    override suspend fun getListing(
        parentCategoryId: String,
    ): AppResult<List<ItemListingModel>, DataError> {
        return try {
            val listings = firestore.collection(EndPoints.ITEMS_LISTING)
                .where(Filter.equalTo(CommonParams.CATEGORY_ID, parentCategoryId)).get().await()
            val items = listings.map { it.toObject<ItemListingDto>().toItemListingModel() }
            AppResult.Done(items)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }

    }
}