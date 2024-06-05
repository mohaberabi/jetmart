package com.mohaberabi.jetmart.features.address.presentation.viewmodel.save

import com.mohaberabi.jetmart.core.util.UiText

sealed interface AddAddressEvents {

    data object AddressSaved : AddAddressEvents

    data class Error(val error: UiText) : AddAddressEvents
}