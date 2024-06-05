package com.mohaberabi.jetmart.features.cart.domain.sync

interface CartSyncer {


    suspend fun syncCartData()
}