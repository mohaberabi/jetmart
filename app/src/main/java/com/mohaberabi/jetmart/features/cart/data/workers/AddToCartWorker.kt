package com.mohaberabi.jetmart.features.cart.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.CartWorkerInputData
import com.mohaberabi.jetmart.core.util.WorkManagerConst
import com.mohaberabi.jetmart.core.util.error.toWorkerResult
import com.mohaberabi.jetmart.features.cart.domain.source.local.PendingCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.remote.CartRemoteDataSource

class AddToCartWorker(
    context: Context,
    private val params: WorkerParameters,
    private val cartRemoteDataSource: CartRemoteDataSource,
    private val pendingCartLocalDataSource: PendingCartLocalDataSource
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {


        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        val pendingCartItemId =
            params.inputData.getString(CartWorkerInputData.CART_ITEM_ID)
                ?: return Result.failure()


        val pendingCartItem =
            pendingCartLocalDataSource.getPendingCartItemById(pendingCartItemId)
                ?: return Result.failure()


        val item = pendingCartItem.item

        return when (val res =
            cartRemoteDataSource.addToCart(item = item, uid = pendingCartItem.userId)) {

            is AppResult.Error -> res.error.toWorkerResult()
            is AppResult.Done -> {
                pendingCartLocalDataSource.deletePendingCartItem(pendingCartItemId)
                Result.success()
            }
        }

    }
}