package com.mohaberabi.jetmart.features.address.data.source.local

import com.mohaberabi.jetmart.core.database.daos.PendingAddressDao
import com.mohaberabi.jetmart.core.database.entity.toPendingAddress
import com.mohaberabi.jetmart.core.database.entity.toPendingAddressEntity
import com.mohaberabi.jetmart.features.address.domain.model.PendingAddressModel
import com.mohaberabi.jetmart.features.address.domain.source.local.PendingAddressLocalDataSource

class RoomPendingAddressLocalDataSource(
    private val pendingAddressDao: PendingAddressDao,
) : PendingAddressLocalDataSource {
    override suspend fun getAllPendingAddresses(): List<PendingAddressModel> =
        pendingAddressDao.getAllPendingAddresses().map { it.toPendingAddress() }

    override suspend fun insertPendingAddress(address: PendingAddressModel) =
        pendingAddressDao.insertPendingAddress(address.toPendingAddressEntity())

    override suspend fun deletePendingAddress(id: String) =
        pendingAddressDao.deletePendingAddress(id)

    override suspend fun getPendingAddressById(id: String): PendingAddressModel? =
        pendingAddressDao.getPendingCartItemById(id)?.toPendingAddress()
}