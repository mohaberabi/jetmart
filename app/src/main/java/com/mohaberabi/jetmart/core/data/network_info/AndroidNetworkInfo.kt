package com.mohaberabi.jetmart.core.data.network_info

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import com.mohaberabi.jetmart.core.domain.network_info.NetworkInfo
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class AndroidNetworkInfo(
    private val context: Context,
    private val dispatchers: DispatchersProvider
) : NetworkInfo {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()


    override fun isConnected(): Flow<Boolean> = callbackFlow<Boolean> {

        if (connectivityManager == null) {
            channel.trySend(false)
            channel.close()
            return@callbackFlow
        }
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true).isSuccess
            }

            override fun onLost(network: Network) {
                trySend(false).isSuccess
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                val hasInternetCapability =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                trySend(hasInternetCapability).isSuccess
            }
        }
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }.flowOn(dispatchers.io).conflate()
       
}