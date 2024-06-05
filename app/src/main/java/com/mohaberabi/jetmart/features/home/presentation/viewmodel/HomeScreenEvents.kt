package com.mohaberabi.jetmart.features.home.presentation.viewmodel

sealed interface HomeScreenEvents {


    data object GoSignIn : HomeScreenEvents

    data object ShowAddressPicker : HomeScreenEvents
}