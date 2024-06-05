package com.mohaberabi.jetmart.features.item.di

import androidx.appcompat.view.menu.MenuView.ItemView
import com.mohaberabi.jetmart.features.item.data.repository.DefaultItemRepository
import com.mohaberabi.jetmart.features.item.data.source.remote.FirebaseItemRemoteDataSource
import com.mohaberabi.jetmart.features.item.domain.repository.ItemRepository
import com.mohaberabi.jetmart.features.item.domain.source.ItemRemoteDataSource
import com.mohaberabi.jetmart.features.item.presentation.viewmodel.ItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val itemModule = module {


    single<ItemRemoteDataSource> {
        FirebaseItemRemoteDataSource(get())
    }

    single<ItemRepository> {
        DefaultItemRepository(get())
    }

    viewModelOf(::ItemViewModel)
}