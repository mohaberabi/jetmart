package com.mohaberabi.jetmart.features.order.domain.source.local

import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import kotlinx.coroutines.flow.Flow

interface OrderLocalDataSource {


    fun getAllOrdersOverview(): Flow<List<OrderOverViewModel>>


    suspend fun insertAllOrders(orders: List<OrderOverViewModel>): EmptyDataResult<DataError>


    suspend fun getOrdersLength(): Int
    suspend fun clearAllOrders()

    suspend fun insertOrderOverView(order: OrderOverViewModel): EmptyDataResult<DataError>

}