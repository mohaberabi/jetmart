package com.mohaberabi.jetmart.features.cart.data.source.local

import com.mohaberabi.jetmart.core.database.daos.PendingCartItemDao
import com.mohaberabi.jetmart.core.database.entity.toPendingCartItem
import com.mohaberabi.jetmart.core.database.entity.toPendingCartItemEntity
import com.mohaberabi.jetmart.features.cart.domain.model.PendingCartItem
import com.mohaberabi.jetmart.features.cart.domain.source.local.PendingCartLocalDataSource

class RoomPendingCartLocalDataSource(
    private val pendingCartItemDao: PendingCartItemDao,
) : PendingCartLocalDataSource {
    override suspend fun getAllPendingCartItems(): List<PendingCartItem> =
        pendingCartItemDao.getAllPendingCartItems().map { it.toPendingCartItem() }

    override suspend fun getPendingCartItemById(itemId: String): PendingCartItem? =
        pendingCartItemDao.getPendingCartItemById(itemId)?.toPendingCartItem()

    override suspend fun upsertPendingCartItem(item: PendingCartItem) =
        pendingCartItemDao.upsertPendingCartItem(item.toPendingCartItemEntity())

    override suspend fun deletePendingCartItem(id: String) =
        pendingCartItemDao.deletePendingCartItem(id)
}