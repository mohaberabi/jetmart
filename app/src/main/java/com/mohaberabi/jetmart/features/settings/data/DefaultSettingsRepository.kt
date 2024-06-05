package com.mohaberabi.jetmart.features.settings.data

import com.mohaberabi.jetmart.core.data.source.JetMartDataStore
import com.mohaberabi.jetmart.core.domain.source.local.JeetMartPrefsLocalDataSource
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.features.address.domain.source.local.AddressLocalDataSource
import com.mohaberabi.jetmart.features.auth.domain.source.remote.UserRemoteDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.local.CartLocalDataSource
import com.mohaberabi.jetmart.features.order.domain.source.local.OrderLocalDataSource
import com.mohaberabi.jetmart.features.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.withContext

class DefaultSettingsRepository(
    private val jetMartLocalDataSource: JeetMartPrefsLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val addressLocalDataSource: AddressLocalDataSource,
    private val cartLocalDataSource: CartLocalDataSource,
    private val appScope: CoroutineScope,
    private val dispatchers: DispatchersProvider,
    private val orderLocalDataSource: OrderLocalDataSource,
) : SettingsRepository {
    override suspend fun signOut() {

        withContext(dispatchers.io) {
            try {
                val remoteSignOutDeferred = appScope.async { userRemoteDataSource.signOut() }
                val cartDeferred = appScope.async { cartLocalDataSource.clearCart() }
                val addressDeferred = appScope.async { addressLocalDataSource.clearAddresses() }
                val prefsDeferred = appScope.async { jetMartLocalDataSource.clear() }
                val ordersDeferred = appScope.async { orderLocalDataSource.clearAllOrders() }

                joinAll(
                    remoteSignOutDeferred,
                    cartDeferred,
                    addressDeferred,
                    prefsDeferred,
                    ordersDeferred
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}