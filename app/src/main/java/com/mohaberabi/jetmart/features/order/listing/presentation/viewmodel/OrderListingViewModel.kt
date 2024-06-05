package com.mohaberabi.jetmart.features.order.listing.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.UiText
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.core.util.error.mapErrorToUiText
import com.mohaberabi.jetmart.core.util.extensions.asResult
import com.mohaberabi.jetmart.features.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class OrderListingViewModel(
    private val orderRepository: OrderRepository,
) : ViewModel() {

    val state =
        orderRepository.getAllOrders().asResult()
            .map { res ->
                when (res) {
                    is AppResult.Done -> OrderListingState.Done(res.data)
                    is AppResult.Error -> OrderListingState.Error(res.error.asUiText())
                }
            }
            .onStart {
                emit(OrderListingState.Loading)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                OrderListingState.Initial
            )

    init {
        viewModelScope.launch {
            orderRepository.fetchAllOrders(
                forceRefresh = false,
            )
        }
    }

    fun onAction(action: OrderListingActions) {
        when (action) {
            is OrderListingActions.OnOrderClick -> Unit
            OrderListingActions.OnRefresh -> onRefresh()
        }
    }

    private fun onRefresh() {
        viewModelScope.launch {
            orderRepository.fetchAllOrders(forceRefresh = true)
        }
    }
}