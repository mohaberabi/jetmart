package com.mohaberabi.jetmart.features.home_layout.presentation.viewmodel

sealed interface HomeLayoutEvents {


    data object GoSignIn : HomeLayoutEvents

    data object GoToCart : HomeLayoutEvents
}