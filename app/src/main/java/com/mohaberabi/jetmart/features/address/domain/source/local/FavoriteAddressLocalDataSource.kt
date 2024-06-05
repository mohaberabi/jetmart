package com.mohaberabi.jetmart.features.address.domain.source.local

import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import kotlinx.coroutines.flow.Flow

interface FavoriteAddressLocalDataSource {


    suspend fun saveAddress(address: AddressModel)
    fun getFavoriteAddress(): Flow<AddressModel?>
}