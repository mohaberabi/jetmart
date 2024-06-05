package com.mohaberabi.jetmart.features.address.presentation.viewmodel.location

import com.mohaberabi.jetmart.core.domain.location.JetMartLocation


sealed interface AddressLocationActions {


    data class OnSaveAddress(
        val location: JetMartLocation,
        val geocodedLocation: String
    ) :
        AddressLocationActions

    data object DismissLocationDialog : AddressLocationActions
    data class SubmitLocationPermission(
        val showRational: Boolean,
    ) : AddressLocationActions

    data object OnMyLocationPressed : AddressLocationActions


    data class OnLocationChanged(
        val location: JetMartLocation,
    ) : AddressLocationActions
}