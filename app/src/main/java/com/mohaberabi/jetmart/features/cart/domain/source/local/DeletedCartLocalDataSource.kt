package com.mohaberabi.jetmart.features.cart.domain.source.local

import androidx.room.Delete
import com.mohaberabi.jetmart.features.cart.domain.model.DeletedCartItem

interface DeletedCartLocalDataSource {


    suspend fun getAllDeletedCartItems(): List<DeletedCartItem>
    suspend fun deleteDeletedCartItem(itemId: String)
    suspend fun insertDeletedCartItem(item: DeletedCartItem)
    suspend fun getDeletedCartItemById(id: String): DeletedCartItem?
}