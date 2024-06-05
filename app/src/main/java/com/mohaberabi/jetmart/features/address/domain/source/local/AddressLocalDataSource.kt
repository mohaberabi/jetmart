package com.mohaberabi.jetmart.features.address.domain.source.local

import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import kotlinx.coroutines.flow.Flow

interface AddressLocalDataSource {


    fun getAllAddress(): Flow<List<AddressModel>>

    suspend fun addAddress(address: AddressModel): EmptyDataResult<DataError>

    suspend fun clearAddresses()

    suspend fun getAddressCount(): Int
    suspend fun deleteAddress(id: String)

    suspend fun insertAllAddresses(addresses: List<AddressModel>): EmptyDataResult<DataError>
}