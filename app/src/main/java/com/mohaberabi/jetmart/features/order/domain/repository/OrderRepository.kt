package com.mohaberabi.jetmart.features.order.domain.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod
import com.mohaberabi.jetmart.features.order.domain.model.OrderItemModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun cancelOrder(
        id: String
    ): EmptyDataResult<DataError>

    fun trackOrder(
        orderId: String,
    ): Flow<OrderModel>

    suspend fun fetchAllOrders(
        forceRefresh: Boolean,
    ): EmptyDataResult<DataError>

    fun getAllOrders(): Flow<List<OrderOverViewModel>>
    suspend fun createOrder(
        address: AddressModel,
        items: List<OrderItemModel>,
        payment: PaymentMethod,
        time: OrderTime,
        deliverFees: Double,
    ): AppResult<String, DataError>
}