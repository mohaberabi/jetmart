package com.mohaberabi.jetmart.features.order.tracking.presenation.viewmodel

import com.mohaberabi.jetmart.core.util.UiText

sealed interface OrderTrackingEvents {


    data class Error(val error: UiText) : OrderTrackingEvents
}