package com.mohaberabi.jetmart.features.cart.domain.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import com.mohaberabi.jetmart.features.cart.domain.model.CartModel
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface CartRepository {


    fun getItemById(itemId: String): Flow<CartItemModel?>

    fun getCart(): Flow<CartModel>
    suspend fun addToCart(
        item: CartItemModel
    ): EmptyDataResult<DataError>

    suspend fun removeItemFromCart(
        itemId: String
    ): EmptyDataResult<DataError>

    suspend fun enqueueCartSync(interval: Duration)
    suspend fun updateCartItem(
        item: CartItemModel
    ): EmptyDataResult<DataError>

    suspend fun clearCart(): EmptyDataResult<DataError>


    suspend fun fetchCart(): EmptyDataResult<DataError>

    suspend fun syncCartData()

    fun getCartLength(): Flow<Int>
}