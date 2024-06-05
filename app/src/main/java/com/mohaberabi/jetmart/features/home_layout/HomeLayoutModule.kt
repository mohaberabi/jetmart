package com.mohaberabi.jetmart.features.home_layout

import com.mohaberabi.jetmart.features.home.presentation.viewmodel.HomeScreenViewModel
import com.mohaberabi.jetmart.features.home_layout.presentation.viewmodel.HomeLayoutViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val homeLayoutModule = module {

    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::HomeLayoutViewModel)
}