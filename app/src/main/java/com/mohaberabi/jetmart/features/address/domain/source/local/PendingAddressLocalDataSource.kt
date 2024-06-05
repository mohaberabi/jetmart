package com.mohaberabi.jetmart.features.address.domain.source.local

import com.mohaberabi.jetmart.features.address.domain.model.PendingAddressModel

interface PendingAddressLocalDataSource {


    suspend fun getAllPendingAddresses(): List<PendingAddressModel>


    suspend fun insertPendingAddress(address: PendingAddressModel)

    suspend fun deletePendingAddress(id: String)
    suspend fun getPendingAddressById(id: String): PendingAddressModel?
}