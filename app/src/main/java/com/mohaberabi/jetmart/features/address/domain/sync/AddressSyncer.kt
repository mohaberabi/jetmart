package com.mohaberabi.jetmart.features.address.domain.sync

interface AddressSyncer {


    suspend fun syncAddressData()
}