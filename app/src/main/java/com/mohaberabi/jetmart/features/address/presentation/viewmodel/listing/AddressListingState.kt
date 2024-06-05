package com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing

import com.mohaberabi.jetmart.features.address.domain.model.AddressModel

data class AddressListingState(


    val addresses: List<AddressModel> = listOf(),
    val favoriteAddress: AddressModel? = null,
    val loading: Boolean = false,
)
