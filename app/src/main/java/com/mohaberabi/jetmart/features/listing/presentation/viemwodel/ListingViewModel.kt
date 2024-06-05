package com.mohaberabi.jetmart.features.listing.presentation.viemwodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.features.cart.domain.model.CartModel
import com.mohaberabi.jetmart.features.cart.domain.repository.CartRepository
import com.mohaberabi.jetmart.features.listing.domain.repository.ListingRepository
import com.mohaberabi.jetmart.features.listing.presentation.navigation.ListingRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListingViewModel(
    savedStateHandle: SavedStateHandle,
    private val listingRepository: ListingRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    val cartState = cartRepository.getCart().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        CartModel()
    )
    private val index = savedStateHandle.toRoute<ListingRoute>().index
    private val parentCategories =
        savedStateHandle.toRoute<ListingRoute>().decodedCategories()
    var parentCategoriesState by mutableStateOf(
        ListingCategoryState(
            parentCategories = parentCategories,
            index = index
        )
    )
        private set
    private val _itemsListingState = MutableStateFlow<ListingItemsState>(ListingItemsState.Initial)
    val itemsListingState = _itemsListingState.asStateFlow()

    init {
        getListings(
            parentCategoriesState.index
        )
    }

    fun onAction(action: ListingActions) {
        when (action) {
            is ListingActions.OnParentCategoryClicked -> getListings(action.index)
            is ListingActions.OnItemClicked -> Unit
            else -> Unit
        }
    }


    private fun getListings(
        index: Int
    ) {
        val id = parentCategories[index].id
        parentCategoriesState = parentCategoriesState.copy(index = index)
        _itemsListingState.update { ListingItemsState.Loading }
        viewModelScope.launch {
            when (val res = listingRepository.getListing(id)) {
                is AppResult.Done -> _itemsListingState.update { ListingItemsState.Done(res.data) }
                is AppResult.Error -> _itemsListingState.update { ListingItemsState.Error(res.error.asUiText()) }
            }
        }
    }
}