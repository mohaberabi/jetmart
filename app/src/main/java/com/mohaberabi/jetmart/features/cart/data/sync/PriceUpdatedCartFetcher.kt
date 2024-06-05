package com.mohaberabi.jetmart.features.cart.data.sync

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.asEmptyResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.auth.domain.source.local.UserLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.local.CartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.remote.CartRemoteDataSource
import com.mohaberabi.jetmart.features.cart.domain.sync.CartFetcher
import com.mohaberabi.jetmart.features.item.domain.source.ItemRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PriceUpdatedCartFetcher(
    private val cartRemoteDataSource: CartRemoteDataSource,
    private val cartLocalDataSource: CartLocalDataSource,
    private val dispatchers: DispatchersProvider,
    private val applicationScope: CoroutineScope,
    private val userLocalDataSource: UserLocalDataSource,
    private val itemsRemoteDataSource: ItemRemoteDataSource
) : CartFetcher {
    override suspend fun fetchCart(): EmptyDataResult<DataError> {
        return withContext(dispatchers.io) {
            val uid = userLocalDataSource.getUserId()!!
            when (val liveSavedCartItemsRes = cartRemoteDataSource.fetchCart(uid)) {
                is AppResult.Done -> {
                    val liveSavedCartItemsIds = liveSavedCartItemsRes.data
                    val liveItemsRes =
                        itemsRemoteDataSource.getItemsWhereIn(liveSavedCartItemsIds.map { it.id })

                    when (liveItemsRes) {
                        is AppResult.Done -> {
                            val liveItemsAsMap = liveItemsRes.data.associateBy({ it.id }, { it })
                            val freshItems =
                                liveSavedCartItemsIds.mapNotNull { savedRemoteCartItem ->
                                    val itemFromCartItem = liveItemsAsMap[savedRemoteCartItem.id]
                                    itemFromCartItem?.let { newItem ->
                                        savedRemoteCartItem.copy(
                                            didPriceChange = savedRemoteCartItem.price != newItem.price,
                                            price = newItem.price
                                        )
                                    }
                                }
                            applicationScope.launch {
                                cartLocalDataSource.insertAllCartItems(freshItems)
                            }.join()
                            AppResult.Done(Unit)
                        }

                        is AppResult.Error -> liveItemsRes.asEmptyResult()
                    }
                }

                is AppResult.Error -> liveSavedCartItemsRes.asEmptyResult()
            }


            AppResult.Done(Unit)
        }
    }
}