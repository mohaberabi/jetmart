package com.mohaberabi.jetmart.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.jetmart.core.database.entity.DeletedAddressEntity
import com.mohaberabi.jetmart.core.database.entity.PendingAddressEntity
import com.mohaberabi.jetmart.features.address.domain.model.DeletedAddressModel


@Dao
interface DeletedAddressDao {


    @Query("SELECT * FROM deletedAddress")
    suspend fun getAllDeletedAddress(): List<DeletedAddressEntity>


    @Upsert
    suspend fun insertDeletedAddress(address: DeletedAddressEntity)

    @Query("DELETE FROM deletedAddress WHERE addressId=:id")
    suspend fun deleteDeletedAddress(id: String)


    @Query("SELECT * FROM deletedAddress WHERE addressId=:id")

    suspend fun getDeletedAddressById(id: String): DeletedAddressEntity?

}