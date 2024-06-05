package com.mohaberabi.jetmart.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.jetmart.core.database.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector


@Dao
interface CartDao {


    @Query("SELECT * FROM cart")
    fun getAllItems(): Flow<List<CartItemEntity>>

    @Query("DELETE FROM cart")
    suspend fun clearCart()


    @Query("SELECT * FROM cart WHERE id=:itemId")
    fun getItemById(itemId: String): Flow<CartItemEntity?>

    @Query("DELETE FROM cart WHERE id =:id")
    suspend fun deleteItem(id: String)

    @Upsert
    suspend fun addToCart(item: CartItemEntity)

    @Upsert
    suspend fun insertAllCartItems(items: List<CartItemEntity>)


    @Query("SELECT COUNT (*) FROM cart")
    fun getCartLength(): Flow<Int>
}