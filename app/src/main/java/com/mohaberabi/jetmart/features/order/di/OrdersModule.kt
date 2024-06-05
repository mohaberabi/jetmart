package com.mohaberabi.jetmart.features.order.di

import com.mohaberabi.jetmart.features.order.data.repository.DefaultOrderRepository
import com.mohaberabi.jetmart.features.order.data.source.local.RoomOrderLocalDataSource
import com.mohaberabi.jetmart.features.order.data.source.remote.FirebaseOrderRemoteDataSource
import com.mohaberabi.jetmart.features.order.domain.repository.OrderRepository
import com.mohaberabi.jetmart.features.order.domain.source.local.OrderLocalDataSource
import com.mohaberabi.jetmart.features.order.domain.source.remote.OrderRemoteDataSource
import com.mohaberabi.jetmart.features.order.listing.presentation.viewmodel.OrderListingViewModel
import com.mohaberabi.jetmart.features.order.tracking.presenation.viewmodel.OrderTrackingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val ordersModule = module {


    single<OrderLocalDataSource> {
        RoomOrderLocalDataSource(get())
    }

    single<OrderRemoteDataSource> {
        FirebaseOrderRemoteDataSource(get())
    }
    singleOf(::DefaultOrderRepository).bind<OrderRepository>()


    viewModelOf(::OrderTrackingViewModel)
    viewModelOf(::OrderListingViewModel)
}