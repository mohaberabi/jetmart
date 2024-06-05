package com.mohaberabi.jetmart.features.address.data.source.local

import com.mohaberabi.jetmart.core.database.daos.DeletedAddressDao
import com.mohaberabi.jetmart.core.database.entity.toDeletedAddress
import com.mohaberabi.jetmart.core.database.entity.toDeletedAddressEntity
import com.mohaberabi.jetmart.features.address.domain.model.DeletedAddressModel
import com.mohaberabi.jetmart.features.address.domain.source.local.DeletedAddressLocalDataSource

class RoomDeletedAddressLocalDataSource(
    private val deletedAddressDao: DeletedAddressDao,
) : DeletedAddressLocalDataSource {
    override suspend fun getAllDeletedAddress(): List<DeletedAddressModel> =
        deletedAddressDao.getAllDeletedAddress().map { it.toDeletedAddress() }

    override suspend fun insertDeletedAddress(address: DeletedAddressModel) =
        deletedAddressDao.insertDeletedAddress(address.toDeletedAddressEntity())

    override suspend fun deleteDeletedAddress(id: String) =
        deletedAddressDao.deleteDeletedAddress(id)

    override suspend fun getDeletedAddressById(id: String): DeletedAddressModel? =
        deletedAddressDao.getDeletedAddressById(id)?.toDeletedAddress()
}