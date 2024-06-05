package com.mohaberabi.jetmart.features.item.presentation.viewmodel

import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.UiText
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.core.util.fold
import com.mohaberabi.jetmart.features.auth.domain.repository.UserRepository
import com.mohaberabi.jetmart.features.cart.domain.repository.CartRepository
import com.mohaberabi.jetmart.features.item.domain.model.toCartItemModel
import com.mohaberabi.jetmart.features.item.domain.repository.ItemRepository
import com.mohaberabi.jetmart.features.item.presentation.navigation.ItemRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemViewModel(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ItemState())
    val itemState = _state.asStateFlow()
    private val itemId = savedStateHandle.toRoute<ItemRoute>().itemId

    init {


        cartRepository.getItemById(itemId)
            .combine(cartRepository.getCartLength()) { inCartItem, cartSize ->
                _state.update { it.copy(cartItem = inCartItem, cartSize = cartSize) }
            }.launchIn(viewModelScope)
        getItem(itemId)
    }


    private val _event = Channel<ItemEvents>()
    val event = _event.receiveAsFlow()
    fun onAction(action: ItemActions) {
        when (action) {
            ItemActions.OnAddToCart -> onAddToCart()
            ItemActions.OnDecQty -> decQty()
            ItemActions.OnIncQty -> incQty()
            ItemActions.OnCartClick -> Unit
        }
    }


    private fun addToCart() {
        _state.update { it.copy(loadingCart = true) }
        viewModelScope.launch {
            val res = cartRepository.addToCart(_state.value.item!!.toCartItemModel())
            res.fold(
                whenError = {
                    _event.send(ItemEvents.Error(it.asUiText()))

                },
            )
        }
        _state.update { it.copy(loadingCart = false) }

    }

    private fun decQty() {
        _state.update { it.copy(loadingCart = true) }
        viewModelScope.launch {
            val item = _state.value.cartItem
            if (item!!.qty == 1) {
                val removeRes = cartRepository.removeItemFromCart(item.id)
                removeRes.fold(whenError = {
                    _event.send(ItemEvents.Error(it.asUiText()))

                })
            } else {
                val decRes = cartRepository.updateCartItem(item.copy(qty = item.qty - 1))
                decRes.fold(whenError = {
                    _event.send(ItemEvents.Error(it.asUiText()))

                })
            }
            _state.update { it.copy(loadingCart = false) }
        }
    }

    private fun incQty() {
        _state.update { it.copy(loadingCart = true) }

        viewModelScope.launch {
            val item = _state.value.cartItem
            val res = cartRepository.updateCartItem(item!!.copy(qty = item.qty + 1))
            res.fold(
                whenError = {
                    _event.send(ItemEvents.Error(it.asUiText()))
                },
            )
            _state.update { it.copy(loadingCart = false) }
        }
    }

    private fun getItem(id: String) {
        _state.update { it.copy(loading = true) }

        viewModelScope.launch {
            val res = itemRepository.getItem(id)
            if (res is AppResult.Done) {
                _state.update { it.copy(item = res.data) }
            }
        }
        _state.update { it.copy(loading = false) }
    }


    private fun onAddToCart() {
        viewModelScope.launch {
            val uid = userRepository.getUserId()
            uid?.let {
                addToCart()
            } ?: _event.send(ItemEvents.GoSignIn)
        }
    }


}