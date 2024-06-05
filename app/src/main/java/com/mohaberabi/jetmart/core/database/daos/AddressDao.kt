package com.mohaberabi.jetmart.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.jetmart.core.database.entity.AddressEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AddressDao {
    @Upsert
    suspend fun addAllAddresses(address: List<AddressEntity>)

    @Upsert
    suspend fun addAddress(address: AddressEntity)

    @Query("DELETE FROM address WHERE id=:id")
    suspend fun deleteAddress(id: String)

    @Query("SELECT * FROM address")
    fun getAllAddresses(): Flow<List<AddressEntity>>

    @Query("DELETE FROM address")
    suspend fun clearAllAddresses()

    @Query("SELECT COUNT (*) FROM address")
    suspend fun getAddressLength(): Int

}