package com.mohaberabi.jetmart.features.order.listing.presentation.viewmodel

import com.google.firestore.v1.StructuredQuery.Order
import com.mohaberabi.jetmart.core.util.UiText
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel


sealed interface OrderListingState {
    data object Loading : OrderListingState
    data object Initial : OrderListingState
    data class Error(val error: UiText) : OrderListingState
    data class Done(val orders: List<OrderOverViewModel>) : OrderListingState
}