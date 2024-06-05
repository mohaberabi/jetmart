package com.mohaberabi.jetmart.features.cart.data.source.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.const.CommonParams
import com.mohaberabi.jetmart.core.util.const.EndPoints
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.core.util.error.fromFirebaseFirestoreException
import com.mohaberabi.jetmart.features.cart.data.source.remote.dto.CartItemDto
import com.mohaberabi.jetmart.features.cart.data.source.remote.dto.toCartDto
import com.mohaberabi.jetmart.features.cart.data.source.remote.dto.toCartItemModel
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import com.mohaberabi.jetmart.features.cart.domain.source.remote.CartRemoteDataSource
import com.mohaberabi.jetmart.features.item.data.source.remote.dto.ItemDto
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseCartRemoteDataSource(
    private val firestore: FirebaseFirestore,
) : CartRemoteDataSource {


    override suspend fun fetchCart(
        uid: String,
    ): AppResult<List<CartItemModel>, DataError> {
        return try {
            val existingItems = carts
                .document(uid)
                .collection(EndPoints.CART_ITEMS)
                .get()
                .await()
                .map { it.toObject<CartItemDto>() }
            AppResult.Done(existingItems.map { it.toCartItemModel() })
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }

    override suspend fun addToCart(
        uid: String,
        item: CartItemModel
    ): EmptyDataResult<DataError> {
        return try {
            carts.document(uid).set(CartUid(uid))
            carts.document(uid)
                .collection(EndPoints.CART_ITEMS).document(item.id)
                .set(item.toCartDto()).await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }

    override suspend fun removeCartItem(
        uid: String, itemId: String,
    ): EmptyDataResult<DataError> {
        return try {
            carts.document(uid)
                .collection(EndPoints.CART_ITEMS)
                .document(itemId)
                .delete().await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }

    override suspend fun updateCartItem(
        uid: String,
        item: CartItemModel
    ): EmptyDataResult<DataError> {
        return try {
            carts.document(uid)
                .collection(EndPoints.CART_ITEMS)
                .document(item.id)
                .set(item).await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }


    override suspend fun clearCart(
        uid: String,
    ): EmptyDataResult<DataError> {
        return try {
            val collection = carts.document(uid).collection(EndPoints.CART_ITEMS)
            val batch = firestore.batch()
            val docs = collection.get().await()
            for (doc in docs) {
                batch.delete(collection.document(doc.id))
            }
            batch.commit().await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }


    private val carts: CollectionReference
        get() = firestore.collection(EndPoints.CARTS)

    private data class CartUid(
        val uid: String
    ) {
        constructor() : this("")
    }

}