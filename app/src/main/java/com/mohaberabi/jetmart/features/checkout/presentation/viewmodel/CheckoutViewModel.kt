package com.mohaberabi.jetmart.features.checkout.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.UiText
import com.mohaberabi.jetmart.core.util.error.AppError
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.features.address.domain.repository.AddressRepository
import com.mohaberabi.jetmart.features.cart.domain.model.toOrderItemModel
import com.mohaberabi.jetmart.features.cart.domain.repository.CartRepository
import com.mohaberabi.jetmart.features.order.domain.repository.OrderRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class CheckoutViewModel(
    private val addressRepository: AddressRepository,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
) : ViewModel() {


    private val _state = MutableStateFlow(CheckoutState())
    val state = _state.asStateFlow()
    private val _event = Channel<CheckoutEvents>()
    val event = _event.receiveAsFlow()

    init {
        addressRepository.getFavoriteAddress()
            .combine(cartRepository.getCart()) { favoriteAddress, cart ->
                _state.update {
                    it.copy(
                        cart = cart,
                        choosedAddress = favoriteAddress
                    )
                }
            }.launchIn(viewModelScope)
    }


    fun onAction(action: CheckoutActions) {
        when (action) {

            CheckoutActions.OnConfirmOrder -> createOrder()
        }
    }


    private var createOrderJob: Job? = null

    private fun createOrder() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {

            val stateVal = _state.value
            val address = stateVal.choosedAddress!!
            val items = stateVal.cart.items.values.map { it.toOrderItemModel() }
            val res = orderRepository.createOrder(
                address = address,
                items = items,
                payment = stateVal.paymentMethod,
                time = stateVal.orderTime,
                deliverFees = stateVal.deliveryFees,
            )
            when (res) {
                is AppResult.Done -> {
                    _event.send(CheckoutEvents.OrderCreated(res.data))
                    _state.update { it.copy(loading = false) }
                }

                is AppResult.Error -> {
                    _event.send(CheckoutEvents.Error(res.error.asUiText()))
                    _state.update { it.copy(loading = false) }
                }

            }


        }


    }
}