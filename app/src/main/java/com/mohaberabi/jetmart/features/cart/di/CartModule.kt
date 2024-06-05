package com.mohaberabi.jetmart.features.cart.di

import com.mohaberabi.jetmart.features.cart.data.repository.OfflineFirstCartRepository
import com.mohaberabi.jetmart.features.cart.data.source.local.RoomCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.data.source.local.RoomDeletedCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.data.source.local.RoomPendingCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.data.source.remote.FirebaseCartRemoteDataSource
import com.mohaberabi.jetmart.features.cart.data.sync.DefaultCartSyncer
import com.mohaberabi.jetmart.features.cart.data.sync.DefaultCartSyncerEnqueuer
import com.mohaberabi.jetmart.features.cart.data.sync.PriceUpdatedCartFetcher
import com.mohaberabi.jetmart.features.cart.data.workers.AddToCartWorker
import com.mohaberabi.jetmart.features.cart.data.workers.FetchCartWorker
import com.mohaberabi.jetmart.features.cart.data.workers.RemoveCartWorker
import com.mohaberabi.jetmart.features.cart.domain.repository.CartRepository
import com.mohaberabi.jetmart.features.cart.domain.source.local.CartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.local.DeletedCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.local.PendingCartLocalDataSource
import com.mohaberabi.jetmart.features.cart.domain.source.remote.CartRemoteDataSource
import com.mohaberabi.jetmart.features.cart.domain.sync.CartFetcher
import com.mohaberabi.jetmart.features.cart.domain.sync.CartSyncer
import com.mohaberabi.jetmart.features.cart.domain.sync.CartSyncerEnqueuer
import com.mohaberabi.jetmart.features.cart.presentation.viewmodel.CartViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val cartModule = module {

    single<DeletedCartLocalDataSource> {
        RoomDeletedCartLocalDataSource(get())
    }
    single<PendingCartLocalDataSource> {
        RoomPendingCartLocalDataSource(get())
    }
    single<CartLocalDataSource> {
        RoomCartLocalDataSource(
            cartItemDao = get()
        )
    }
    single<CartRemoteDataSource> {
        FirebaseCartRemoteDataSource(
            firestore = get(),
        )
    }
    singleOf(::DefaultCartSyncerEnqueuer).bind<CartSyncerEnqueuer>()
    singleOf(::PriceUpdatedCartFetcher).bind<CartFetcher>()
    singleOf(::DefaultCartSyncer).bind<CartSyncer>()
    singleOf(::OfflineFirstCartRepository).bind<CartRepository>()
    workerOf(::AddToCartWorker)
    workerOf(::FetchCartWorker)
    workerOf(::RemoveCartWorker)
    viewModelOf(::CartViewModel)

}