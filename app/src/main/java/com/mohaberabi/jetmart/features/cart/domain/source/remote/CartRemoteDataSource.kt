package com.mohaberabi.jetmart.features.cart.domain.source.remote

import android.provider.ContactsContract.Data
import androidx.datastore.preferences.protobuf.Empty
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel

interface CartRemoteDataSource {
    suspend fun addToCart(
        uid: String,
        item: CartItemModel
    ): EmptyDataResult<DataError>

    suspend fun removeCartItem(
        uid: String,
        itemId: String
    ): EmptyDataResult<DataError>

    suspend fun updateCartItem(
        uid: String,
        item: CartItemModel,
    )
            : EmptyDataResult<DataError>

    suspend fun clearCart(uid: String): EmptyDataResult<DataError>


    suspend fun fetchCart(uid: String): AppResult<List<CartItemModel>, DataError>


}