package com.mohaberabi.jetmart.features.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.core.util.fold
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import com.mohaberabi.jetmart.features.cart.domain.repository.CartRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _event = Channel<CartEvents>()

    val event = _event.receiveAsFlow()
    val state = cartRepository.getCart().map {
        CartState(cart = it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        CartState()
    )

    init {
        viewModelScope.launch {
            cartRepository.syncCartData()
        }

        viewModelScope.launch {
            cartRepository.fetchCart()
        }
        viewModelScope.launch {
            cartRepository.enqueueCartSync(30.minutes)
        }
    }

    fun onAction(action: CartActions) {
        when (action) {
            CartActions.OnClearCartClicked -> clearCart()
            is CartActions.OnDecrementQty -> {
                if (action.item.qty == 1) {
                    removeCartItem(action.item.id)
                } else {
                    updateItem(action.item.copy(qty = action.item.qty - 1))
                }
            }

            is CartActions.OnIncrementQty -> updateItem(action.item.copy(qty = action.item.qty + 1))
            else -> Unit
        }
    }


    private fun removeCartItem(id: String) {
        viewModelScope.launch {

            val res = cartRepository.removeItemFromCart(id)
            res.fold {
                _event.send(CartEvents.Error(it.asUiText()))

            }
        }
    }

    private fun clearCart() {
        viewModelScope.launch {
            val res = cartRepository.clearCart()
            res.fold {
                _event.send(CartEvents.Error(it.asUiText()))
            }
        }
    }

    private fun updateItem(item: CartItemModel) {
        viewModelScope.launch {
            val res = cartRepository.updateCartItem(item)
            res.fold {
                _event.send(CartEvents.Error(it.asUiText()))
            }
        }
    }

}