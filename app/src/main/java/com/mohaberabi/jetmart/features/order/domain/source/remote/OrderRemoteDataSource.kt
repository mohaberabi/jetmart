package com.mohaberabi.jetmart.features.order.domain.source.remote

import android.provider.ContactsContract.Data
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.order.domain.model.OrderItemModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import kotlinx.coroutines.flow.Flow

interface OrderRemoteDataSource {


    suspend fun fetchAllOrders(uid: String): AppResult<List<OrderOverViewModel>, DataError>

    suspend fun createOrder(order: OrderModel): AppResult<String, DataError>

    suspend fun cancelOrder(orderId: String): EmptyDataResult<DataError>

    fun trackOrder(id: String): Flow<OrderModel>
}