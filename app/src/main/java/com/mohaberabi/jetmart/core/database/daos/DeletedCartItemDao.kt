package com.mohaberabi.jetmart.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.jetmart.core.database.entity.DeleteCartEntity
import com.mohaberabi.jetmart.features.cart.domain.model.DeletedCartItem


@Dao
interface DeletedCartItemDao {


    @Query("SELECT * FROM deleteCartItem")
    suspend fun getAllDeletedCartItems(): List<DeleteCartEntity>


    @Query("DELETE FROM deleteCartItem WHERE cartItemId=:itemId")
    suspend fun deleteDeletedCartItem(itemId: String)


    @Query("SELECT * FROM deleteCartItem WHERE cartItemId=:id")

    suspend fun getDeletedCartItemById(id: String): DeleteCartEntity?

    @Upsert
    suspend fun insertDeletedCartItem(item: DeleteCartEntity)
}