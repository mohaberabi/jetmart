package com.mohaberabi.jetmart.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.jetmart.core.database.entity.PendingCartItemEntity


@Dao
interface PendingCartItemDao {


    @Query("SELECT * FROM pendingCartItem")
    suspend fun getAllPendingCartItems(): List<PendingCartItemEntity>


    @Query("SELECT * FROM pendingCartItem WHERE id=:itemId")
    suspend fun getPendingCartItemById(itemId: String): PendingCartItemEntity?

    @Upsert
    suspend fun upsertPendingCartItem(item: PendingCartItemEntity)

    @Query("DELETE FROM pendingCartItem WHERE id=:id")
    suspend fun deletePendingCartItem(id: String)
}