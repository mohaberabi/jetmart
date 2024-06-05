package com.mohaberabi.jetmart.features.cart.domain.sync

import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError

interface CartFetcher {


    suspend fun fetchCart(): EmptyDataResult<DataError>
}