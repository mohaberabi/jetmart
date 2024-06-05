package com.mohaberabi.jetmart.features.address.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohaberabi.jetmart.core.util.AddressWorkerInputData
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.WorkManagerConst
import com.mohaberabi.jetmart.core.util.error.toWorkerResult
import com.mohaberabi.jetmart.features.address.domain.source.local.DeletedAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.source.remote.AddressRemoteDataSource

class DeleteAddressWorker(
    context: Context,
    private val params: WorkerParameters,
    private val addressRemoteDataSource: AddressRemoteDataSource,
    private val deletedAddressLocalDatasource: DeletedAddressLocalDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {


        if (runAttemptCount >= WorkManagerConst.MAX_ALLOWED_ATTEMPTS) {
            return Result.failure()
        }

        val addressId =
            params.inputData.getString(AddressWorkerInputData.ADDRESS_ID) ?: return Result.failure()


        val deletedAddress = deletedAddressLocalDatasource.getDeletedAddressById(addressId)
            ?: return Result.failure()


        val res = addressRemoteDataSource.deleteAddress(
            uid = deletedAddress.userId,
            addressId = deletedAddress.id
        )
        return when (res) {
            is AppResult.Done -> {
                deletedAddressLocalDatasource.deleteDeletedAddress(addressId)
                Result.success()
            }

            is AppResult.Error -> res.error.toWorkerResult()
        }
    }
}