package com.mohaberabi.jetmart.features.cart.data.source.local

import com.mohaberabi.jetmart.core.database.daos.DeletedCartItemDao
import com.mohaberabi.jetmart.core.database.entity.toDeletedCartItem
import com.mohaberabi.jetmart.core.database.entity.toDeletedCartItemEntity
import com.mohaberabi.jetmart.features.cart.domain.model.DeletedCartItem
import com.mohaberabi.jetmart.features.cart.domain.source.local.DeletedCartLocalDataSource

class RoomDeletedCartLocalDataSource(
    private val deletedCartItemDao: DeletedCartItemDao,
) : DeletedCartLocalDataSource {
    override suspend fun getAllDeletedCartItems(): List<DeletedCartItem> =
        deletedCartItemDao.getAllDeletedCartItems().map { it.toDeletedCartItem() }

    override suspend fun deleteDeletedCartItem(itemId: String) =
        deletedCartItemDao.deleteDeletedCartItem(itemId)

    override suspend fun insertDeletedCartItem(item: DeletedCartItem) =
        deletedCartItemDao.insertDeletedCartItem(item.toDeletedCartItemEntity())

    override suspend fun getDeletedCartItemById(id: String): DeletedCartItem? =
        deletedCartItemDao.getDeletedCartItemById(id)?.toDeletedCartItem()
}