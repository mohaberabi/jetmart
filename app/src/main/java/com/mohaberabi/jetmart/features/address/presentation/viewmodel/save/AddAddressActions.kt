package com.mohaberabi.jetmart.features.address.presentation.viewmodel.save

sealed interface AddAddressActions {


    data object OnSaveAddress : AddAddressActions

    data class OnAddressNameChanged(val name: String) : AddAddressActions

    data class OnAddressDetailsChanged(val details: String) : AddAddressActions
}