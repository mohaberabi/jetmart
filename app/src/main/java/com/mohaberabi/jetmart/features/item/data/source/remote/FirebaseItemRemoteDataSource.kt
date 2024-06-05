package com.mohaberabi.jetmart.features.item.data.source.remote

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.mohaberabi.jetmart.core.util.const.EndPoints
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.core.util.error.fromFirebaseFirestoreException
import com.mohaberabi.jetmart.features.item.data.source.remote.dto.ItemDto
import com.mohaberabi.jetmart.features.item.data.source.remote.dto.toItemModel
import com.mohaberabi.jetmart.features.item.domain.model.ItemModel
import com.mohaberabi.jetmart.features.item.domain.source.ItemRemoteDataSource
import kotlinx.coroutines.tasks.await

class FirebaseItemRemoteDataSource(
    private val firestore: FirebaseFirestore,
) : ItemRemoteDataSource {
    override suspend fun getItem(itemId: String): AppResult<ItemModel, DataError> {

        return try {
            val itemData = firestore.collection(EndPoints.ITEMS).document(itemId).get().await()
            if (itemData == null) {
                AppResult.Error(DataError.Network.UNKNOWN_ERROR)
            } else {
                val item = itemData.toObject<ItemDto>()!!.toItemModel()
                AppResult.Done(item)
            }
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)

        }
    }

    override suspend fun getItemsWhereIn(
        itemsIds: List<String>,
    ): AppResult<List<ItemModel>, DataError> {
        return try {
            val itemData = firestore.collection(EndPoints.ITEMS)
                .whereIn(FieldPath.documentId(), itemsIds).get().await()
            if (itemData == null) {
                AppResult.Error(DataError.Network.UNKNOWN_ERROR)
            } else {
                val items = itemData.map { it.toObject<ItemDto>().toItemModel() }
                AppResult.Done(items)
            }
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)

        }
    }
}