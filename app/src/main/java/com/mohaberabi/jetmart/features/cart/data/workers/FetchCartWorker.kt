package com.mohaberabi.jetmart.features.cart.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.WorkManagerConst
import com.mohaberabi.jetmart.core.util.error.toWorkerResult
import com.mohaberabi.jetmart.features.cart.domain.sync.CartFetcher

class FetchCartWorker(
    context: Context,
    params: WorkerParameters,
    private val cartFetcher: CartFetcher
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= WorkManagerConst.MAX_ALLOWED_ATTEMPTS) {
            return Result.failure()
        }
        return when (val res = cartFetcher.fetchCart()) {
            is AppResult.Done -> Result.success()
            is AppResult.Error -> res.error.toWorkerResult()
        }
    }
}