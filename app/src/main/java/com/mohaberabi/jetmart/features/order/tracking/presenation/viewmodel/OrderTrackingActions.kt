package com.mohaberabi.jetmart.features.order.tracking.presenation.viewmodel

sealed interface OrderTrackingActions {


    data object OnCancelOrder : OrderTrackingActions

}