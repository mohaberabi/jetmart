package com.mohaberabi.jetmart.features.address.presentation.viewmodel.save

import com.mohaberabi.jetmart.core.util.const.AppConst

data class AddAddressState(
    val lat: Double = AppConst.defaultAppLocation.lat,
    val lng: Double = AppConst.defaultAppLocation.lng,
    val locationName: String = "",
    val addressName: String = "",
    val address: String = "",
    val loading: Boolean = false
) {
    val canSaveAddress: Boolean
        get() = addressName.isNotEmpty() && address.isNotEmpty()
}
