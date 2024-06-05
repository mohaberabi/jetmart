package com.mohaberabi.jetmart.features.address.domain.repository

import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import kotlinx.coroutines.flow.Flow

interface AddressRepository {


    fun getAllAddresses(): Flow<List<AddressModel>>

    suspend fun addAddress(address: AddressModel): EmptyDataResult<DataError>
    suspend fun deleteAddress(id: String): EmptyDataResult<DataError>

    suspend fun saveFavoriteAddress(address: AddressModel): EmptyDataResult<DataError>
    fun getFavoriteAddress(): Flow<AddressModel?>
    suspend fun fetchAddresses(): EmptyDataResult<DataError>
}