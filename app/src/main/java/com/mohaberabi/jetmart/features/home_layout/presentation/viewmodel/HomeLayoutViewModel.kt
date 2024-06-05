package com.mohaberabi.jetmart.features.home_layout.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.features.auth.domain.repository.UserRepository
import com.mohaberabi.jetmart.features.cart.domain.repository.CartRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeLayoutViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    val _event = Channel<HomeLayoutEvents>()
    val event = _event.receiveAsFlow()

    val state = cartRepository.getCartLength().map {
        HomeLayoutState(cartSize = it)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            HomeLayoutState()
        )


    fun onAction(action: HomeLayoutActions) {
        when (action) {
            HomeLayoutActions.OnCartClicked -> onCartClicked()
        }
    }

    private fun onCartClicked() {
        viewModelScope.launch {
            val uid = userRepository.getUserId()
            uid?.let {
                _event.send(HomeLayoutEvents.GoToCart)
            } ?: _event.send(HomeLayoutEvents.GoSignIn)
        }
    }
}