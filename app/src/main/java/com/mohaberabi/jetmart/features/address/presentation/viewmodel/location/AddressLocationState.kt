package com.mohaberabi.jetmart.features.address.presentation.viewmodel.location

import com.mohaberabi.jetmart.core.util.const.AppConst
import com.mohaberabi.jetmart.core.domain.location.JetMartLocation


data class AddressLocationState(

    val showLocationRational: Boolean = false,
    val locationAllowed: Boolean = true,
    val currentLocation: JetMartLocation? = null,
    val choosedLocation: JetMartLocation = AppConst.defaultAppLocation,
    val geocodedAddress: String = "",
    val loading: Boolean = false
)