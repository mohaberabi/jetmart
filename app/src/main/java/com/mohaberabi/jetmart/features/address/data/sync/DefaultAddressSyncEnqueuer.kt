package com.mohaberabi.jetmart.features.address.data.sync

import android.content.Context
import androidx.work.Data
import androidx.work.WorkManager
import androidx.work.await
import com.mohaberabi.jetmart.core.util.AddressWorkerInputData
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.util.jetMartOneTimeWorkerRequest
import com.mohaberabi.jetmart.features.address.data.worker.AddAddressWorker
import com.mohaberabi.jetmart.features.address.data.worker.DeleteAddressWorker
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.address.domain.model.DeletedAddressModel
import com.mohaberabi.jetmart.features.address.domain.model.PendingAddressModel
import com.mohaberabi.jetmart.features.address.domain.source.local.DeletedAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.source.local.PendingAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.sync.AddressSyncEnqueuer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultAddressSyncEnqueuer(
    private val context: Context,
    private val dispatchers: DispatchersProvider,
    private val deletedAddressLocalDataSource: DeletedAddressLocalDataSource,
    private val pendingAddressLocalDataSource: PendingAddressLocalDataSource,
    private val appScope: CoroutineScope,
) : AddressSyncEnqueuer {
    private val workManager = WorkManager.getInstance(context)
    override suspend fun enqueueSync(type: AddressSyncEnqueuer.AddressSyncType) {

        when (type) {
            is AddressSyncEnqueuer.AddressSyncType.DeleteAddress -> enqueueDeleteAddress(
                addressId = type.id,
                uid = type.uid
            )

            is AddressSyncEnqueuer.AddressSyncType.UpsertAddress -> enqueueAddAddress(
                userId = type.uid,
                address = type.address
            )
        }
    }

    override suspend fun clearAllSync() {

        appScope.launch {
            workManager.cancelAllWorkByTag(AddressWorkerInputData.DELETE_WORK_TAG).await()
            workManager.cancelAllWorkByTag(AddressWorkerInputData.CREATE_WORK_TAG).await()
        }
    }


    private suspend fun enqueueAddAddress(
        userId: String,
        address: AddressModel,
    ) {
        withContext(dispatchers.io) {
            val pendingAddress = PendingAddressModel(
                address = address,
                userId = userId
            )
            pendingAddressLocalDataSource.insertPendingAddress(pendingAddress)

            val request = workManager.jetMartOneTimeWorkerRequest<AddAddressWorker>(
                tag = AddressWorkerInputData.CREATE_WORK_TAG,
                inputs = Data.Builder().putString(
                    AddressWorkerInputData.ADDRESS_ID,
                    address.id
                )
            ).build()
            appScope.launch {
                workManager.enqueue(request)
            }.join()


        }
    }

    private suspend fun enqueueDeleteAddress(
        addressId: String,
        uid: String,
    ) {

        withContext(dispatchers.io) {

            val deletedAddress = DeletedAddressModel(
                id = addressId,
                userId = uid
            )
            deletedAddressLocalDataSource.insertDeletedAddress(deletedAddress)
            val request = workManager.jetMartOneTimeWorkerRequest<DeleteAddressWorker>(
                tag = AddressWorkerInputData.DELETE_WORK_TAG,
                inputs = Data.Builder().putString(
                    AddressWorkerInputData.ADDRESS_ID,
                    addressId
                )
            ).build()

            appScope.launch {
                workManager.enqueue(request)
            }.join()


        }
    }
}