package com.mohaberabi.jetmart.features.order.listing.presentation.viewmodel


sealed interface OrderListingActions {
    data class OnOrderClick(val id: String) : OrderListingActions
    data object OnRefresh : OrderListingActions
}