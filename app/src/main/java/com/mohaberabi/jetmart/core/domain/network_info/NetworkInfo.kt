package com.mohaberabi.jetmart.core.domain.network_info

import kotlinx.coroutines.flow.Flow

interface NetworkInfo {


    fun isConnected(): Flow<Boolean>
}