package com.mohaberabi.jetmart.features.checkout.di

import com.mohaberabi.jetmart.features.checkout.presentation.viewmodel.CheckoutViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val checkoutModule = module {


    viewModelOf(::CheckoutViewModel)

}