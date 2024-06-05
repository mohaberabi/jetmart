package com.mohaberabi.jetmart.features.cart.data.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.asEmptyResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.auth.domain.source.local.UserLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import com.mohaberabi.jetmart.features.cart.domain.model.CartModel
import com.mohaberabi.jetmart.features.cart.domain.repository.CartRepository
import com.mohaberabi.jetmart.features.cart.domain.source.local.CartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.remote.CartRemoteDataSource
import com.mohaberabi.jetmart.features.cart.domain.sync.CartFetcher
import com.mohaberabi.jetmart.features.cart.domain.sync.CartSyncer
import com.mohaberabi.jetmart.features.cart.domain.sync.CartSyncerEnqueuer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration

class OfflineFirstCartRepository(
    private val appScope: CoroutineScope,
    private val userLocalDataSource: UserLocalDataSource,
    private val cartRemoteDataSource: CartRemoteDataSource,
    private val cartLocalDataSource: CartLocalDataSource,
    private val cartSyncerEnqueuer: CartSyncerEnqueuer,
    private val cartFetcher: CartFetcher,
    private val cartSyncer: CartSyncer,
) : CartRepository {
    override fun getItemById(itemId: String): Flow<CartItemModel?> =
        cartLocalDataSource.getItemById(itemId)

    override suspend fun fetchCart(): EmptyDataResult<DataError> = cartFetcher.fetchCart()
    override suspend fun syncCartData() = cartSyncer.syncCartData()
    override fun getCartLength(): Flow<Int> = cartLocalDataSource.getCartLength()

    override fun getCart(): Flow<CartModel> = cartLocalDataSource
        .getAllCartItems().map { list ->
            val itemsMap = list.associateBy { it.id }
            CartModel(items = itemsMap)
        }

    override suspend fun addToCart(
        item: CartItemModel,
    ): EmptyDataResult<DataError> {

        val id = userLocalDataSource.getUserId()!!

        val localRes = cartLocalDataSource.addToCart(item)
        if (localRes !is AppResult.Done) {
            return localRes.asEmptyResult()
        }

        return when (val remoteRes = cartRemoteDataSource.addToCart(uid = id, item = item)) {
            is AppResult.Error -> {
                appScope.launch {
                    cartSyncerEnqueuer.enqueueSync(
                        CartSyncerEnqueuer.CartSyncType.UpserCartItem(
                            item,
                            id
                        )
                    )
                }.join()
                AppResult.Done(Unit)
            }

            is AppResult.Done -> remoteRes.asEmptyResult()
        }
    }

    override suspend fun removeItemFromCart(
        itemId: String,
    ): EmptyDataResult<DataError> {

        val localRes = cartLocalDataSource.removeCartItem(itemId)

        if (localRes !is AppResult.Done) {
            return localRes.asEmptyResult()
        }

        val id = userLocalDataSource.getUserId()!!
        return when (val remoteRes =
            cartRemoteDataSource.removeCartItem(uid = id, itemId = itemId)) {
            is AppResult.Error -> {
                appScope.launch {
                    cartSyncerEnqueuer.enqueueSync(
                        type = CartSyncerEnqueuer.CartSyncType.RemoveCartItem(
                            itemId, id
                        )
                    )
                }.join()
                AppResult.Done(Unit)
            }

            is AppResult.Done -> remoteRes.asEmptyResult()
        }
    }

    override suspend fun enqueueCartSync(interval: Duration) =
        cartSyncerEnqueuer.enqueueSync(
            CartSyncerEnqueuer.CartSyncType.FetchCart(duration = interval)
        )

    override suspend fun updateCartItem(
        item: CartItemModel,
    ): EmptyDataResult<DataError> {
        val localRes = cartLocalDataSource.updateCartItem(item)

        if (localRes !is AppResult.Done) {
            return localRes.asEmptyResult()
        }

        val id = userLocalDataSource.getUserId()!!
        return when (val remoteRes = cartRemoteDataSource.addToCart(uid = id, item = item)) {
            is AppResult.Error -> {
                appScope.launch {
                    cartSyncerEnqueuer.enqueueSync(
                        CartSyncerEnqueuer.CartSyncType.UpserCartItem(
                            item,
                            id
                        )
                    )
                }.join()
                AppResult.Done(Unit)
            }

            is AppResult.Done -> remoteRes.asEmptyResult()
        }
    }

    override suspend fun clearCart(
    ): EmptyDataResult<DataError> {
        val id = userLocalDataSource.getUserId()!!
        val localDeferred = appScope.async { cartLocalDataSource.clearCart() }
        val remoteDeferred = appScope.async { cartRemoteDataSource.clearCart(id) }
        val syncDeferred = appScope.async { cartSyncerEnqueuer.cancelAllSyncs() }
        awaitAll(localDeferred, remoteDeferred, syncDeferred)
        return AppResult.Done(Unit)
    }


}