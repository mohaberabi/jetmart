package com.mohaberabi.jetmart.features.address.data.source.local

import android.database.sqlite.SQLiteFullException
import com.mohaberabi.jetmart.core.database.daos.AddressDao
import com.mohaberabi.jetmart.core.database.entity.toAddressEntity
import com.mohaberabi.jetmart.core.database.entity.toAddressModel
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.address.domain.source.local.AddressLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomAddressLocalDataSource(
    private val addresssDao: AddressDao,
) : AddressLocalDataSource {
    override fun getAllAddress(): Flow<List<AddressModel>> =
        addresssDao.getAllAddresses().map { list -> list.map { it.toAddressModel() } }

    override suspend fun clearAddresses() = addresssDao.clearAllAddresses()
    override suspend fun addAddress(address: AddressModel): EmptyDataResult<DataError> {
        return try {
            addresssDao.addAddress(address.toAddressEntity())
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getAddressCount(): Int = addresssDao.getAddressLength()

    override suspend fun deleteAddress(id: String) {
        addresssDao.deleteAddress(id)
    }

    override suspend fun insertAllAddresses(addresses: List<AddressModel>): EmptyDataResult<DataError> {
        return try {
            addresssDao.addAllAddresses(addresses.map { it.toAddressEntity() })
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }
}