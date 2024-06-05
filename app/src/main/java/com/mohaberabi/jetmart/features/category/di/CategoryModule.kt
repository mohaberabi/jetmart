package com.mohaberabi.jetmart.features.category.di

import com.mohaberabi.jetmart.features.category.data.repository.DefaultCategoriesRepository
import com.mohaberabi.jetmart.features.category.data.source.remote.FirebaseCategoriesRemoteDataSource
import com.mohaberabi.jetmart.features.category.domain.repository.CategoriesRepository
import com.mohaberabi.jetmart.features.category.domain.source.remote.CategoriesRemoteDataSource
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val categoryModule = module {

    single<CategoriesRemoteDataSource> {
        FirebaseCategoriesRemoteDataSource(get())
    }

    single<CategoriesRepository> {
        DefaultCategoriesRepository(get())
    }


}