package com.mohaberabi.jetmart.features.address.presentation.viewmodel.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.core.domain.geocoder.JetMartGeoCoder
import com.mohaberabi.jetmart.core.domain.location.JetMartLocation
import com.mohaberabi.jetmart.core.domain.location.JetMartLocationProvider
import com.mohaberabi.jetmart.core.util.AppResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AddressLocationViewModel(
    private val jetMartLocationProvider: JetMartLocationProvider,
    private val jetMartGeoCoder: JetMartGeoCoder
) : ViewModel() {


    private val _event = Channel<AddressLocationEvents>()
    val event = _event.receiveAsFlow()
    private val _state = MutableStateFlow(AddressLocationState())
    val state = _state.asStateFlow()
    fun onAction(action: AddressLocationActions) {
        when (action) {
            AddressLocationActions.DismissLocationDialog -> dismissDialog()
            is AddressLocationActions.SubmitLocationPermission -> submitLocationPermission(
                action.showRational
            )

            AddressLocationActions.OnMyLocationPressed -> requestCurrentLocation()
            is AddressLocationActions.OnLocationChanged -> geoCodeLocation(action.location)
            is AddressLocationActions.OnSaveAddress -> Unit
        }
    }


    private fun requestCurrentLocation() {
        viewModelScope.launch {
            val didAllow = jetMartLocationProvider.isLocationPermisisonAllowed()
            if (didAllow) {
                val location = jetMartLocationProvider.getCurrentLocation()
                location?.let { loc ->
                    _state.update { it.copy(currentLocation = loc) }
                } ?: _state.update { it.copy(showLocationRational = true) }
            } else {
                _state.update { it.copy(showLocationRational = true) }
            }

        }
    }


    private fun geoCodeLocation(
        location: JetMartLocation,
    ) {

        _state.update { it.copy(loading = true) }
        viewModelScope.launch {

            when (val res =
                jetMartGeoCoder.geocodeFromLatLng(lng = location.lng, lat = location.lat)) {
                is AppResult.Done -> {
                    _state.update { it.copy(geocodedAddress = res.data) }
                }

                is AppResult.Error -> _event.send(AddressLocationEvents.Error())
            }
            _state.update { it.copy(loading = false) }

        }
    }

    private fun dismissDialog() = _state.update { it.copy(showLocationRational = false) }


    private fun submitLocationPermission(
        showRational: Boolean
    ) =
        _state.update {
            it.copy(
                showLocationRational = showRational
            )
        }
}
