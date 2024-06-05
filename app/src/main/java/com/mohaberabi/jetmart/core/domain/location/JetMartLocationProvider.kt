package com.mohaberabi.jetmart.core.domain.location

import kotlinx.coroutines.flow.Flow

interface JetMartLocationProvider {


    fun listenToLocation(): Flow<JetMartLocation>

    fun isLocationPermisisonAllowed(): Boolean
    suspend fun getCurrentLocation(): JetMartLocation?
}