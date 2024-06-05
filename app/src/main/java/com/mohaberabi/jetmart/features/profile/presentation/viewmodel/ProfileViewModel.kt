package com.mohaberabi.jetmart.features.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.features.auth.domain.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {


    private val _event = Channel<ProfileEvent>()
    val event = _event.receiveAsFlow()


    private val _state = MutableStateFlow(ProfileState())

    val state = _state.asStateFlow()

    init {
        userRepository.getUserData().map { user ->
            _state.update { it.copy(user = user) }
        }.launchIn(viewModelScope)
    }


    fun onAction(action: ProfileActions) {
        when (action) {
            is ProfileActions.OnLastNameChanged -> lastNameChanged(action.lastname)
            is ProfileActions.OnNameChanged -> nameChanged(action.name)
            ProfileActions.OnSaveClick -> updateUser()
        }
    }


    private fun nameChanged(name: String) =
        _state.update { it.copy(user = it.user.copy(name = name)) }

    private fun lastNameChanged(lastName: String) =
        _state.update { it.copy(user = it.user.copy(lastname = lastName)) }


    private fun updateUser() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val res = userRepository.updateUserData(_state.value.user)) {
                is AppResult.Done -> _event.send(ProfileEvent.Updated)
                is AppResult.Error -> _event.send(ProfileEvent.Error(res.error.asUiText()))
            }
        }
        _state.update { it.copy(loading = false) }
    }
}