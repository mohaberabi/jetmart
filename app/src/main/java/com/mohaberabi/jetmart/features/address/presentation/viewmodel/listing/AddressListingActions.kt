package com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing

import com.mohaberabi.jetmart.features.address.domain.model.AddressModel

sealed interface AddressListingActions {


    data class OnDeleteAddress(val id: String) : AddressListingActions
    data class OnMarkAsFavorite(val address: AddressModel) : AddressListingActions
}