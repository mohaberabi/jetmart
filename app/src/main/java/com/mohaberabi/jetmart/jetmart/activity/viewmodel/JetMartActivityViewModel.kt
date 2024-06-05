package com.mohaberabi.jetmart.jetmart.activity.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jetmart.core.domain.network_info.NetworkInfo
import com.mohaberabi.jetmart.core.domain.repository.JetMartPrefsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JetMartActivityViewModel(
    private val jetMartPrefsRepository: JetMartPrefsRepository,
    private val networkInfo: NetworkInfo
) : ViewModel() {
    var state: StateFlow<JetMartActivityState> =
        jetMartPrefsRepository.getJetMartPrefs()
            .map { prefs ->
                JetMartActivityState(
                    sawOnBoarding = prefs.seenOnBoarding,
                    didLoadData = true,
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                JetMartActivityState()
            )

    val hasNetwork = networkInfo.isConnected().map { it }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        true,
    )
}