package com.mohaberabi.jetmart.features.order.data.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.asEmptyResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.auth.domain.source.local.UserLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.local.CartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.remote.CartRemoteDataSource
import com.mohaberabi.jetmart.features.cart.domain.sync.CartSyncerEnqueuer
import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod
import com.mohaberabi.jetmart.features.order.domain.model.OrderItemModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import com.mohaberabi.jetmart.features.order.domain.model.toOrderOverView
import com.mohaberabi.jetmart.features.order.domain.repository.OrderRepository
import com.mohaberabi.jetmart.features.order.domain.source.local.OrderLocalDataSource
import com.mohaberabi.jetmart.features.order.domain.source.remote.OrderRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZonedDateTime

class DefaultOrderRepository(
    private val userLocalDataSource: UserLocalDataSource,
    private val orderRemoteDataSource: OrderRemoteDataSource,
    private val orderLocalDataSource: OrderLocalDataSource,
    private val appScope: CoroutineScope,
    private val cartSyncerEnqueuer: CartSyncerEnqueuer,
    private val cartLocalDataSource: CartLocalDataSource,
    private val cartRemoteDataSource: CartRemoteDataSource,
    private val dispatchers: DispatchersProvider
) : OrderRepository {
    override suspend fun cancelOrder(
        id: String,
    ): EmptyDataResult<DataError> =
        orderRemoteDataSource.cancelOrder(orderId = id)

    override fun trackOrder(orderId: String): Flow<OrderModel> =
        orderRemoteDataSource.trackOrder(orderId)

    override fun getAllOrders(): Flow<List<OrderOverViewModel>> =
        orderLocalDataSource.getAllOrdersOverview()

    override suspend fun fetchAllOrders(
        forceRefresh: Boolean,
    ): EmptyDataResult<DataError> {

        val uid = userLocalDataSource.getUserId() ?: return AppResult.Done(Unit)
        val length = orderLocalDataSource.getOrdersLength()

        return if (length == 0 || forceRefresh) {
            when (val res = orderRemoteDataSource.fetchAllOrders(uid)) {
                is AppResult.Done -> {
                    appScope.launch {
                        orderLocalDataSource.insertAllOrders(orders = res.data)
                    }.join()
                    AppResult.Done(Unit)
                }

                is AppResult.Error -> res.asEmptyResult()
            }
        } else {
            AppResult.Done(Unit)
        }


    }

    override suspend fun createOrder(
        address: AddressModel,
        items: List<OrderItemModel>,
        payment: PaymentMethod,
        time: OrderTime,
        deliverFees: Double
    ): AppResult<String, DataError> {

        val user = userLocalDataSource.getUserData().first()
        if (user.isEmpty) {
            return AppResult.Error(DataError.Local.UNKNOWN)
        } else {
            val order = OrderModel(
                items = items,
                address = address,
                deliveryFees = deliverFees,
                orderTime = time,
                paymentMethod = payment,
                createdAt = ZonedDateTime.now(),
                uid = user.uid,
                user = user
            )
            val remoteRes = orderRemoteDataSource.createOrder(order)
            if (remoteRes is AppResult.Done) {
                postCheckout(
                    order.toOrderOverView(),
                    uid = user.uid
                )
            }
            return remoteRes
        }
    }

    private suspend fun postCheckout(
        order: OrderOverViewModel,
        uid: String
    ) =
        withContext(dispatchers.io) {
            val insertOrderDeferred =
                appScope.async { orderLocalDataSource.insertOrderOverView(order) }
            val clearLocalCartDeferred = appScope.async { cartLocalDataSource.clearCart() }
            val clearRemoteCartDeferred = appScope.async { cartRemoteDataSource.clearCart(uid) }
            val clearSyncDeferred = appScope.async { cartSyncerEnqueuer.cancelAllSyncs() }
            awaitAll(
                insertOrderDeferred,
                clearLocalCartDeferred,
                clearRemoteCartDeferred,
                clearSyncDeferred,
            )

        }
}