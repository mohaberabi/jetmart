package com.mohaberabi.jetmart.features.cart.domain.source.local

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import kotlinx.coroutines.flow.Flow

interface CartLocalDataSource {

    fun getAllCartItems(): Flow<List<CartItemModel>>

    suspend fun insertAllCartItems(items: List<CartItemModel>): EmptyDataResult<DataError.Local>
    suspend fun addToCart(
        item: CartItemModel
    ): EmptyDataResult<DataError.Local>

    suspend fun removeCartItem(

        itemId: String
    ): EmptyDataResult<DataError.Local>

    suspend fun updateCartItem(

        item: CartItemModel,
    )
            : EmptyDataResult<DataError.Local>

    suspend fun clearCart()

    fun getItemById(itemId: String): Flow<CartItemModel?>


    fun getCartLength(): Flow<Int>
}