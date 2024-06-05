package com.mohaberabi.jetmart.features.order.tracking.presenation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.UiText
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.core.util.error.mapErrorToUiText
import com.mohaberabi.jetmart.core.util.extensions.asResult
import com.mohaberabi.jetmart.features.order.domain.model.OrderModel
import com.mohaberabi.jetmart.features.order.domain.repository.OrderRepository
import com.mohaberabi.jetmart.features.order.tracking.presenation.navigation.OrderTrackingRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderTrackingViewModel(
    private val orderRepository: OrderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val orderId = savedStateHandle.toRoute<OrderTrackingRoute>().orderId
    val state: StateFlow<OrderTrackingState> =
        orderRepository.trackOrder(orderId)
            .asResult()
            .map { res ->
                when (res) {
                    is AppResult.Done -> OrderTrackingState.Done(res.data)
                    is AppResult.Error -> OrderTrackingState.Error(res.error.asUiText())
                }
            }
            .onStart { emit(OrderTrackingState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = OrderTrackingState.Initial
            )

    private val _event = Channel<OrderTrackingEvents>()
    val event = _event.receiveAsFlow()
    fun onAction(action: OrderTrackingActions) {
        when (action) {
            OrderTrackingActions.OnCancelOrder -> cancelOrder()
        }
    }

    private fun cancelOrder() {
        viewModelScope.launch {
            val res = orderRepository.cancelOrder(orderId)
            if (res is AppResult.Error) {
                _event.send(OrderTrackingEvents.Error(res.error.asUiText()))
            }
        }
    }

}