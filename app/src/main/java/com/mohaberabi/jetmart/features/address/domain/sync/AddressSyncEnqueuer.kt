package com.mohaberabi.jetmart.features.address.domain.sync

import com.mohaberabi.jetmart.features.address.domain.model.AddressModel

interface AddressSyncEnqueuer {


    suspend fun enqueueSync(type: AddressSyncType)

    suspend fun clearAllSync()
    sealed class AddressSyncType {


        data class DeleteAddress(
            val id: String,
            val uid: String
        ) : AddressSyncType()

        data class UpsertAddress(
            val address: AddressModel,
            val uid: String
        ) : AddressSyncType()


    }


}