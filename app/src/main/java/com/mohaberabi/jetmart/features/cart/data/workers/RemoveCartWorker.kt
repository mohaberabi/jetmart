package com.mohaberabi.jetmart.features.cart.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohaberabi.jetmart.core.database.daos.DeletedCartItemDao
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.CartWorkerInputData
import com.mohaberabi.jetmart.core.util.WorkManagerConst
import com.mohaberabi.jetmart.core.util.error.toWorkerResult
import com.mohaberabi.jetmart.features.cart.domain.source.local.DeletedCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.remote.CartRemoteDataSource

class RemoveCartWorker(
    context: Context,
    private val params: WorkerParameters,
    private val cartRemoteDataSource: CartRemoteDataSource,
    private val deletedCartLocalDataSource: DeletedCartLocalDataSource
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= WorkManagerConst.MAX_ALLOWED_ATTEMPTS) {
            return Result.failure()
        }

        val itemId =
            params.inputData.getString(CartWorkerInputData.CART_ITEM_ID) ?: return Result.failure()
        val item =
            deletedCartLocalDataSource.getDeletedCartItemById(itemId) ?: return Result.failure()
        return when (val res =
            cartRemoteDataSource.removeCartItem(itemId = itemId, uid = item.userId)) {
            is AppResult.Error -> res.error.toWorkerResult()
            is AppResult.Done -> {
                deletedCartLocalDataSource.deleteDeletedCartItem(itemId = itemId)
                Result.success()
            }
        }
    }
}