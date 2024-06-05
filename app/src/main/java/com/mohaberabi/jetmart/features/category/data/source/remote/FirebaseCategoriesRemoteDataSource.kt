package com.mohaberabi.jetmart.features.category.data.source.remote

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.mohaberabi.jetmart.core.util.const.CommonParams
import com.mohaberabi.jetmart.core.util.const.EndPoints
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.core.util.error.fromFirebaseFirestoreException
import com.mohaberabi.jetmart.features.category.data.source.remote.dto.CategoryDto
import com.mohaberabi.jetmart.features.category.data.source.remote.dto.toCategoryModel
import com.mohaberabi.jetmart.features.category.domain.model.CategoryModel
import com.mohaberabi.jetmart.features.category.domain.source.remote.CategoriesRemoteDataSource
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FirebaseCategoriesRemoteDataSource(
    private val firestore: FirebaseFirestore,
) : CategoriesRemoteDataSource {

    override suspend fun getCategories(): AppResult<List<CategoryModel>, DataError> {

        return try {
            val catsSnapshot = firestore.collection(EndPoints.CATEGORIES).get().await()
            val cats = catsSnapshot.map { it.toObject<CategoryDto>().toCategoryModel() }
            AppResult.Done(cats)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }

    override suspend fun getCategoriesByParentId(parentId: String): AppResult<List<CategoryModel>, DataError> {
        return try {
            val cats = firestore.collection(EndPoints.CATEGORIES)
                .where(Filter.equalTo(CommonParams.PARENT_ID, parentId))
                .get()
                .await()
                .map {
                    it.toObject<CategoryDto>().toCategoryModel()
                }
            AppResult.Done(cats)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }
}
