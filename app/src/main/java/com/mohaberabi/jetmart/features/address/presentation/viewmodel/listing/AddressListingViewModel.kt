package com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddressListingViewModel(
    private val addressRepository: AddressRepository,
) : ViewModel() {

    val state = addressRepository.getAllAddresses()
        .combine(addressRepository.getFavoriteAddress())
        { allAddresses, favorite ->
            AddressListingState(
                addresses = allAddresses,
                favoriteAddress = favorite
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            AddressListingState()
        )

    init {
        viewModelScope.launch {
            addressRepository.fetchAddresses()
        }
    }


    fun onAction(action: AddressListingActions) {
        when (action) {
            is AddressListingActions.OnMarkAsFavorite -> markAsFavorite(action.address)
            is AddressListingActions.OnDeleteAddress -> Unit
        }
    }

    private fun markAsFavorite(address: AddressModel) {
        viewModelScope.launch {
            addressRepository.saveFavoriteAddress(address)
        }
    }
}