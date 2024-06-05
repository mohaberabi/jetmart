package com.mohaberabi.jetmart.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.asUiText
import com.mohaberabi.jetmart.features.auth.domain.repository.UserRepository
import com.mohaberabi.jetmart.features.category.domain.repository.CategoriesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

class HomeScreenViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _categoriesState =
        MutableStateFlow<HomeCategoriesState>(HomeCategoriesState.Initial)
    val categoriesState = _categoriesState.asStateFlow()


    private val _event = Channel<HomeScreenEvents>()


    val event = _event.receiveAsFlow()

    init {

        getCategories()
    }


    fun onAction(action: HomeActions) {
        when (action) {
            HomeActions.OnRetry -> getCategories()
            HomeActions.OnAddressClicked -> onAddressClicked()
            else -> Unit
        }
    }

    private fun getCategories() {
        _categoriesState.update { HomeCategoriesState.Loading }
        viewModelScope.launch {
            when (val res = categoriesRepository.getCategories()) {
                is AppResult.Done -> _categoriesState.update { HomeCategoriesState.Done(res.data) }
                is AppResult.Error -> _categoriesState.update { HomeCategoriesState.Error(res.error.asUiText()) }
            }
        }
    }

    private fun onAddressClicked() {
        viewModelScope.launch {
            val uid = userRepository.getUserId()
            uid?.let {
                _event.send(HomeScreenEvents.ShowAddressPicker)
            } ?: _event.send(HomeScreenEvents.GoSignIn)
        }
    }
}