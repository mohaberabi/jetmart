package com.mohaberabi.jetmart.core.domain.source.local

import com.mohaberabi.jetmart.core.domain.model.JetMartPrefs
import kotlinx.coroutines.flow.Flow

interface JeetMartPrefsLocalDataSource {
    suspend fun sawOnBoarding()
    fun getJetMartPrefs(): Flow<JetMartPrefs>

    suspend fun clear()
}