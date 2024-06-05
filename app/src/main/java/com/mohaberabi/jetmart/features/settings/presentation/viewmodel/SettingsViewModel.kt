package com.mohaberabi.jetmart.features.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.features.auth.domain.repository.UserRepository
import com.mohaberabi.jetmart.features.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _event = Channel<SettingsEvent>()
    val event = _event.receiveAsFlow()

    val state = userRepository.getUserData().map { user ->
        SettingsState(
            user = user
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SettingsState()
    )

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.OnSignOutClick -> viewModelScope.launch {
                settingsRepository.signOut()
                _event.send(SettingsEvent.SignedOut)
            }
        }
    }
}