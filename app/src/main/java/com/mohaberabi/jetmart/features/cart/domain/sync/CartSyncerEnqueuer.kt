package com.mohaberabi.jetmart.features.cart.domain.sync

import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import kotlin.time.Duration

interface CartSyncerEnqueuer {


    suspend fun enqueueSync(type: CartSyncType)
    suspend fun cancelAllSyncs()

    sealed interface CartSyncType {

        data class FetchCart(
            val duration: Duration,
        ) : CartSyncType

        data class UpserCartItem(
            val item: CartItemModel,
            val userId: String,
        ) : CartSyncType

        data class RemoveCartItem(
            val itemId: String,
            val userId: String,
        ) : CartSyncType
    }
}