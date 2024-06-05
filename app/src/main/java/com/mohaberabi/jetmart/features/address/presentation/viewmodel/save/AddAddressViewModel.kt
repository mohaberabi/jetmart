package com.mohaberabi.jetmart.features.address.presentation.viewmodel.save

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.address.domain.repository.AddressRepository
import com.mohaberabi.jetmart.features.address.presentation.navigation.AddAddressRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class AddAddressViewModel(
    savedStateHandle: SavedStateHandle,
    private val addressRepository: AddressRepository,
) : ViewModel() {

    private val routeArgs = AddAddressRoute.fromSavedStateHandle(savedStateHandle).args

    private val latitude = routeArgs.lat
    private val longitude = routeArgs.lng
    private val locationName = routeArgs.locationName
    private val _state = MutableStateFlow(
        AddAddressState(
            lat = latitude,
            lng = longitude,
            locationName = locationName
        )
    )
    val state = _state.asStateFlow()

    private val _event = Channel<AddAddressEvents>()

    val event = _event.receiveAsFlow()
    fun onAction(action: AddAddressActions) {
        when (action) {
            is AddAddressActions.OnAddressDetailsChanged -> addressDetailsChanged(action.details)
            is AddAddressActions.OnAddressNameChanged -> addressNameChanged(action.name)
            AddAddressActions.OnSaveAddress -> saveAddress()
        }
    }


    private fun addressDetailsChanged(details: String) =
        _state.update { it.copy(address = details) }

    private fun addressNameChanged(name: String) = _state.update { it.copy(addressName = name) }


    private fun saveAddress() {
        val stateVal = _state.value
        val address = AddressModel(
            lat = stateVal.lat,
            lng = stateVal.lng,
            address = stateVal.address,
            location = stateVal.locationName,
            label = stateVal.addressName,
            id = UUID.randomUUID().toString()
        )
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val res = addressRepository.addAddress(address)) {
                is AppResult.Done -> _event.send(AddAddressEvents.AddressSaved)
                is AppResult.Error -> _event.send(AddAddressEvents.Error(res.error.asUiText()))
            }
        }
        _state.update { it.copy(loading = false) }


    }

}