package com.mohaberabi.jetmart.features.cart.domain.source.local

import com.mohaberabi.jetmart.features.cart.domain.model.PendingCartItem

interface PendingCartLocalDataSource {


    suspend fun getAllPendingCartItems(): List<PendingCartItem>

    suspend fun getPendingCartItemById(itemId: String): PendingCartItem?


    suspend fun upsertPendingCartItem(item: PendingCartItem)
    suspend fun deletePendingCartItem(id: String)

}