package com.mohaberabi.jetmart.features.order.data.source.local

import android.database.sqlite.SQLiteFullException
import com.mohaberabi.jetmart.core.database.daos.OrderOverViewDao
import com.mohaberabi.jetmart.core.database.entity.toOrderOverViewEntity
import com.mohaberabi.jetmart.core.database.entity.toOrderOverViewModel
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import com.mohaberabi.jetmart.features.order.domain.source.local.OrderLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomOrderLocalDataSource(
    private val ordersDao: OrderOverViewDao,
) : OrderLocalDataSource {
    override fun getAllOrdersOverview(): Flow<List<OrderOverViewModel>> =
        ordersDao.getAllOrderOverViews().map { list -> list.map { it.toOrderOverViewModel() } }

    override suspend fun insertAllOrders(orders: List<OrderOverViewModel>): EmptyDataResult<DataError> {
        return try {
            ordersDao.upsertAllOrderOverviews(orders.map { it.toOrderOverViewEntity() })
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getOrdersLength(): Int = ordersDao.getOrdersLength()

    override suspend fun clearAllOrders() = ordersDao.clearAllOrders()

    override suspend fun insertOrderOverView(order: OrderOverViewModel): EmptyDataResult<DataError> {
        return try {
            ordersDao.insertOrderOverView(order.toOrderOverViewEntity())
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }
}