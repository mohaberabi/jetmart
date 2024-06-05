package com.mohaberabi.jetmart.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.jetmart.core.database.entity.PendingAddressEntity
import com.mohaberabi.jetmart.core.database.entity.PendingCartItemEntity


@Dao
interface PendingAddressDao {


    @Query("SELECT * FROM pendingAddress")
    suspend fun getAllPendingAddresses(): List<PendingAddressEntity>


    @Upsert
    suspend fun insertPendingAddress(address: PendingAddressEntity)

    @Query("DELETE FROM pendingAddress WHERE addressId=:id")
    suspend fun deletePendingAddress(id: String)

    @Query("SELECT * FROM pendingAddress WHERE addressId=:addressId")
    suspend fun getPendingCartItemById(addressId: String): PendingAddressEntity?

}