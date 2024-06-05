package com.mohaberabi.jetmart.features.cart.data.sync

import android.content.Context
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.mohaberabi.jetmart.core.util.CartWorkerInputData
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.util.WorkManagerConst
import com.mohaberabi.jetmart.core.util.jetMartOneTimeWorkerRequest
import com.mohaberabi.jetmart.core.util.jetMartPeriodicWorkerRequest
import com.mohaberabi.jetmart.features.cart.data.workers.AddToCartWorker
import com.mohaberabi.jetmart.features.cart.data.workers.FetchCartWorker
import com.mohaberabi.jetmart.features.cart.data.workers.RemoveCartWorker
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import com.mohaberabi.jetmart.features.cart.domain.model.DeletedCartItem
import com.mohaberabi.jetmart.features.cart.domain.model.PendingCartItem
import com.mohaberabi.jetmart.features.cart.domain.source.local.DeletedCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.local.PendingCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.sync.CartSyncerEnqueuer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class DefaultCartSyncerEnqueuer(
    private val context: Context,
    private val dispatchers: DispatchersProvider,
    private val deleteCartItemLocalDataSource: DeletedCartLocalDataSource,
    private val pendingCartItemLocalDatasource: PendingCartLocalDataSource,
    private val appScope: CoroutineScope
) : CartSyncerEnqueuer {

    private val workManager = WorkManager.getInstance(context)
    override suspend fun enqueueSync(
        type: CartSyncerEnqueuer.CartSyncType,
    ) {

        when (type) {
            is CartSyncerEnqueuer.CartSyncType.FetchCart -> enqueueFetchCartWorker(type.duration)
            is CartSyncerEnqueuer.CartSyncType.RemoveCartItem -> enqueueDeleteCartWorker(
                itemId = type.itemId,
                userId = type.userId
            )

            is CartSyncerEnqueuer.CartSyncType.UpserCartItem -> enqueueUpsertingCartItem(
                item = type.item,
                userId = type.userId
            )
        }
    }

    override suspend fun cancelAllSyncs() {
        val deleteWorkDeferred =
            appScope.async { workManager.cancelAllWorkByTag(CartWorkerInputData.DELETE_WORK_TAG) }
        val syncWorkDeferred =
            appScope.async { workManager.cancelAllWorkByTag(CartWorkerInputData.SYNC_WORK_TAG) }
        val createWorkDeferred =
            appScope.async { workManager.cancelAllWorkByTag(CartWorkerInputData.CREATE_WORK_TAG) }
        awaitAll(deleteWorkDeferred, syncWorkDeferred, createWorkDeferred)
    }


    private suspend fun enqueueUpsertingCartItem(
        item: CartItemModel,
        userId: String,
    ) {
        val pendingCartItem = PendingCartItem(
            userId = userId,
            item = item
        )
        withContext(dispatchers.io) {
            pendingCartItemLocalDatasource.upsertPendingCartItem(pendingCartItem)
            val upsertRequest = workManager.jetMartOneTimeWorkerRequest<AddToCartWorker>(
                tag = CartWorkerInputData.CREATE_WORK_TAG,
                inputs = Data.Builder().putString(
                    CartWorkerInputData.CART_ITEM_ID, item.id
                ).putString(WorkManagerConst.UID_WORKER_INPUT_DATA, userId)
            ).build()
            appScope.launch {
                workManager.enqueue(upsertRequest).await()
            }.join()
        }
    }


    private suspend fun enqueueFetchCartWorker(interval: Duration) {
        withContext(dispatchers.io) {
            val isAlreadySyncing =
                workManager
                    .getWorkInfosByTag(CartWorkerInputData.SYNC_WORK_TAG)
                    .get()
                    .isNotEmpty()
            if (isAlreadySyncing) {
                return@withContext
            }
            val request = workManager.jetMartPeriodicWorkerRequest<FetchCartWorker>(
                tag = CartWorkerInputData.SYNC_WORK_TAG,
                interval = interval
            ).build()
            appScope.launch {
                workManager.enqueue(request).await()
            }.join()

        }

    }

    private suspend fun enqueueDeleteCartWorker(
        itemId: String,
        userId: String
    ) {
        withContext(dispatchers.io) {
            val insertedToBeDeleted = DeletedCartItem(
                cartItemId = itemId,
                userId = userId
            )
            deleteCartItemLocalDataSource.insertDeletedCartItem(
                insertedToBeDeleted
            )
            val deleteWorkerRequest = workManager.jetMartOneTimeWorkerRequest<RemoveCartWorker>(
                tag = CartWorkerInputData.DELETE_WORK_TAG,
                inputs = Data.Builder().putString(CartWorkerInputData.CART_ITEM_ID, itemId)
            ).build()
            appScope.launch {
                workManager.enqueue(deleteWorkerRequest).await()
            }.join()
        }

    }


}