package com.mohaberabi.jetmart.features.cart.data.sync

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.features.auth.domain.source.local.UserLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.local.DeletedCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.local.PendingCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.remote.CartRemoteDataSource
import com.mohaberabi.jetmart.features.cart.domain.sync.CartSyncer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultCartSyncer(
    private val pendingCartLocalDataSource: PendingCartLocalDataSource,
    private val deletedCartLocalDataSource: DeletedCartLocalDataSource,
    private val cartRemoteDataSource: CartRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val appScope: CoroutineScope,
    private val dispatchers: DispatchersProvider,
) : CartSyncer {
    override suspend fun syncCartData() {


        withContext(dispatchers.io) {
            val uid = userLocalDataSource.getUserId() ?: return@withContext
            val waitingToBeAddedDeferred =
                async { pendingCartLocalDataSource.getAllPendingCartItems() }
            val waitingToBeRemovedDeferred =
                async { deletedCartLocalDataSource.getAllDeletedCartItems() }
            val waitingToBeAddedJob = waitingToBeAddedDeferred.await().map { pendingItem ->
                launch {
                    val res = cartRemoteDataSource.addToCart(item = pendingItem.item, uid = uid)
                    when (res) {
                        is AppResult.Done -> {
                            appScope.launch {
                                pendingCartLocalDataSource.deletePendingCartItem(pendingItem.item.id)
                            }.join()
                        }

                        is AppResult.Error -> Unit
                    }
                }
            }
            val waitingToBeRemovedJob = waitingToBeRemovedDeferred.await().map { deleted ->
                launch {
                    val res = cartRemoteDataSource.removeCartItem(
                        uid = uid,
                        itemId = deleted.cartItemId
                    )
                    when (res) {
                        is AppResult.Done -> {
                            deletedCartLocalDataSource.deleteDeletedCartItem(deleted.cartItemId)
                        }

                        is AppResult.Error -> Unit
                    }
                }
            }
            waitingToBeAddedJob.forEach { it.join() }
            waitingToBeRemovedJob.forEach { it.join() }
        }
    }
}