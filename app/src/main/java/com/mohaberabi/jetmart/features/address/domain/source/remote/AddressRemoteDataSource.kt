package com.mohaberabi.jetmart.features.address.domain.source.remote

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel

interface AddressRemoteDataSource {


    suspend fun addAddress(uid: String, address: AddressModel): EmptyDataResult<DataError>
    suspend fun deleteAddress(uid: String, addressId: String): EmptyDataResult<DataError>
    suspend fun getAllAddresses(uid: String): AppResult<List<AddressModel>, DataError>
}