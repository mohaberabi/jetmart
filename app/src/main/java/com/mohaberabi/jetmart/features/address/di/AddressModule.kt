package com.mohaberabi.jetmart.features.address.di

import com.mohaberabi.jetmart.features.address.data.repository.OfflineFirstAddressRepository
import com.mohaberabi.jetmart.features.address.data.source.local.FavoriteAddressDataStore
import com.mohaberabi.jetmart.features.address.data.source.local.RoomAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.data.source.local.RoomDeletedAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.data.source.local.RoomPendingAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.data.source.remote.FirebaseAddressRemoteDataSource
import com.mohaberabi.jetmart.features.address.data.sync.DefaultAddressSyncEnqueuer
import com.mohaberabi.jetmart.features.address.data.worker.AddAddressWorker
import com.mohaberabi.jetmart.features.address.data.worker.DeleteAddressWorker
import com.mohaberabi.jetmart.features.address.domain.repository.AddressRepository
import com.mohaberabi.jetmart.features.address.domain.source.local.AddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.source.local.DeletedAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.source.local.FavoriteAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.source.local.PendingAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.source.remote.AddressRemoteDataSource
import com.mohaberabi.jetmart.features.address.domain.sync.AddressSyncEnqueuer
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.location.AddressLocationViewModel
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing.AddressListingViewModel
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.save.AddAddressViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val addressModule = module {


    single<DeletedAddressLocalDataSource> {

        RoomDeletedAddressLocalDataSource(get())
    }

    single<PendingAddressLocalDataSource> {
        RoomPendingAddressLocalDataSource(get())
    }

    single<FavoriteAddressLocalDataSource> {
        FavoriteAddressDataStore(get(), get())
    }

    single<AddressRemoteDataSource> {
        FirebaseAddressRemoteDataSource(get())
    }
    single<AddressLocalDataSource> {

        RoomAddressLocalDataSource(get())
    }

    singleOf(::DefaultAddressSyncEnqueuer).bind<AddressSyncEnqueuer>()
    singleOf(::OfflineFirstAddressRepository).bind<AddressRepository>()
    viewModelOf(::AddressLocationViewModel)
    viewModelOf(::AddAddressViewModel)
    viewModelOf(::AddressListingViewModel)
    workerOf(::AddAddressWorker)
    workerOf(::DeleteAddressWorker)

}