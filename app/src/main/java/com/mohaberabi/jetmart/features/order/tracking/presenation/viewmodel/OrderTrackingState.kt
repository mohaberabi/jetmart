package com.mohaberabi.jetmart.features.order.tracking.presenation.viewmodel

import com.mohaberabi.jetmart.core.util.UiText
import com.mohaberabi.jetmart.features.order.domain.model.OrderModel

sealed interface OrderTrackingState {


    data object Initial : OrderTrackingState

    data object Loading : OrderTrackingState
    data class Error(val error: UiText) : OrderTrackingState
    data class Done(val order: OrderModel) : OrderTrackingState
}
