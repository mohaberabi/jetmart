package com.mohaberabi.jetmart.features.address.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohaberabi.jetmart.core.util.AddressWorkerInputData
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.WorkManagerConst
import com.mohaberabi.jetmart.core.util.error.toWorkerResult
import com.mohaberabi.jetmart.features.address.domain.source.local.PendingAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.source.remote.AddressRemoteDataSource

class AddAddressWorker(
    context: Context,
    private val params: WorkerParameters,
    private val addressRemoteDataSource: AddressRemoteDataSource,
    private val pendingAddressLocalDataSource: PendingAddressLocalDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= WorkManagerConst.MAX_ALLOWED_ATTEMPTS) {
            return Result.failure()
        }

        val addressId =
            params.inputData.getString(AddressWorkerInputData.ADDRESS_ID) ?: return Result.failure()


        val pendingAddress = pendingAddressLocalDataSource.getPendingAddressById(addressId)
            ?: return Result.failure()

        val res = addressRemoteDataSource.addAddress(
            address = pendingAddress.address,
            uid = pendingAddress.userId
        )

        return when (res) {
            is AppResult.Done -> {
                pendingAddressLocalDataSource.deletePendingAddress(addressId)
                Result.success()
            }

            is AppResult.Error -> res.error.toWorkerResult()
        }

    }
}