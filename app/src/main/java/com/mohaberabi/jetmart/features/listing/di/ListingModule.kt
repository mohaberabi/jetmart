package com.mohaberabi.jetmart.features.listing.di

import com.mohaberabi.jetmart.features.listing.data.repository.DefaultListingRepository
import com.mohaberabi.jetmart.features.listing.data.source.remote.FirebaseListingRemoteDataSource
import com.mohaberabi.jetmart.features.listing.domain.repository.ListingRepository
import com.mohaberabi.jetmart.features.listing.domain.source.ListingRemoteDataSource
import com.mohaberabi.jetmart.features.listing.presentation.viemwodel.ListingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val listingModule = module {


    single<ListingRemoteDataSource> {
        FirebaseListingRemoteDataSource(get())
    }
    single<ListingRepository> {
        DefaultListingRepository(get(), get(), get())
    }
    viewModelOf(::ListingViewModel)
}