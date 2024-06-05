package com.mohaberabi.jetmart.features.address.domain.source.local

import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.jetmart.core.database.entity.DeletedAddressEntity
import com.mohaberabi.jetmart.features.address.domain.model.DeletedAddressModel

interface DeletedAddressLocalDataSource {


    suspend fun getAllDeletedAddress(): List<DeletedAddressModel>


    suspend fun insertDeletedAddress(address: DeletedAddressModel)

    suspend fun deleteDeletedAddress(id: String)


    suspend fun getDeletedAddressById(id: String): DeletedAddressModel?
}