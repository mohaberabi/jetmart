package com.mohaberabi.jetmart.features.order.data.source.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.const.CommonParams
import com.mohaberabi.jetmart.core.util.const.EndPoints
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.core.util.error.fromFirebaseFirestoreException
import com.mohaberabi.jetmart.features.order.data.source.remote.dto.OrderModelDto
import com.mohaberabi.jetmart.features.order.data.source.remote.dto.toOrderDto
import com.mohaberabi.jetmart.features.order.data.source.remote.dto.toOrderModel
import com.mohaberabi.jetmart.features.order.data.source.remote.dto.toOrderOverView
import com.mohaberabi.jetmart.features.order.domain.model.OrderModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderStatus
import com.mohaberabi.jetmart.features.order.domain.source.remote.OrderRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

class FirebaseOrderRemoteDataSource(
    private val firestore: FirebaseFirestore,
) : OrderRemoteDataSource {
    override suspend fun fetchAllOrders(
        uid: String,
    ): AppResult<List<OrderOverViewModel>, DataError> {
        return try {
            val orders = orders.where(Filter.equalTo(CommonParams.UID, uid))
                .get()
                .await()
                .mapNotNull {
                    it.toObject<OrderModelDto>().toOrderOverView()
                }
            AppResult.Done(orders)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun createOrder(
        order: OrderModel,
    ): AppResult<String, DataError> {
        return try {
            orders.document(order.id).set(order.toOrderDto()).await()
            AppResult.Done(order.id)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun cancelOrder(
        orderId: String,
    ): EmptyDataResult<DataError> {

        return try {
            orders.document(orderId).update(
                mapOf(
                    CommonParams.ORDER_STATUS to
                            OrderStatus.CANCELED.name
                )
            ).await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun trackOrder(
        id: String,
    ): Flow<OrderModel> =
        orders.document(id).snapshots().map { it.toObject<OrderModelDto>()!!.toOrderModel() }

    private val orders: CollectionReference
        get() = firestore.collection(EndPoints.ORDERS)
}