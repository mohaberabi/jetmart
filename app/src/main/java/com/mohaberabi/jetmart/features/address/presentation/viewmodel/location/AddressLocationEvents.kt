package com.mohaberabi.jetmart.features.address.presentation.viewmodel.location

import com.mohaberabi.jetmart.core.util.UiText

sealed interface AddressLocationEvents {

    companion object {
        private const val DEFAULT_GEOCODE_ERROR = "Could not get address , please try again..."
    }

    data class Error(
        val error: UiText = UiText.DynamicString(DEFAULT_GEOCODE_ERROR),
    ) :
        AddressLocationEvents
}