package com.mohaberabi.jetmart.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.features.auth.domain.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AuthViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _event = Channel<AuthEvent>()
    val event = _event.receiveAsFlow()
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()
    fun onAction(action: AuthActions) {
        when (action) {
            is AuthActions.OnEmailChanged -> emailChanged(action.email)
            is AuthActions.OnLastNameChanged -> lastnameChanged(action.lastname)
            AuthActions.OnLoginClick -> {
                if (_state.value.isLogin) {
                    login()
                } else {
                    register()
                }
            }

            is AuthActions.OnNameChanged -> nameChanged(action.name)
            is AuthActions.OnPasswordChanged -> passwordChanged(action.password)
            AuthActions.OnToggleAuthWay -> toggleAuthWay()
            else -> Unit
        }
    }


    private fun login() {

        _state.update { it.copy(loading = true) }

        viewModelScope.launch {
            val res =
                userRepository.login(email = _state.value.email, password = _state.value.password)
            when (res) {
                is AppResult.Done -> _event.send(AuthEvent.AuthDone)
                is AppResult.Error -> _event.send(AuthEvent.Error(res.error.asUiText()))
            }
            _state.update { it.copy(loading = false) }

        }
    }

    private fun register() {

        _state.update { it.copy(loading = true) }

        viewModelScope.launch {
            val res =
                userRepository.signUp(
                    email = _state.value.email,
                    password = _state.value.password,
                    name = _state.value.name,
                    lastname = _state.value.lastName,
                )
            when (res) {
                is AppResult.Done -> _event.send(AuthEvent.AuthDone)
                is AppResult.Error -> _event.send(AuthEvent.Error(res.error.asUiText()))
            }
            _state.update { it.copy(loading = false) }

        }
    }

    private fun toggleAuthWay() = _state.update { it.copy(isLogin = !it.isLogin) }
    private fun emailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }

    private fun passwordChanged(password: String) {
        _state.update { it.copy(password = password) }
    }


    private fun nameChanged(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun lastnameChanged(lastName: String) {
        _state.update { it.copy(lastName = lastName) }
    }


}