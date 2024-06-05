package com.mohaberabi.jetmart.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.jetmart.core.database.entity.OrderOverviewEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface OrderOverViewDao {
    @Upsert
    suspend fun insertOrderOverView(order: OrderOverviewEntity)

    @Upsert
    suspend fun upsertAllOrderOverviews(orders: List<OrderOverviewEntity>)

    @Query("SELECT COUNT (*) FROM orders")
    suspend fun getOrdersLength(): Int

    @Query("DELETE FROM orders")
    suspend fun clearAllOrders()

    @Query("SELECT * FROM orders")
    fun getAllOrderOverViews(): Flow<List<OrderOverviewEntity>>
}