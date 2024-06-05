package com.mohaberabi.jetmart.core.domain.repository

import com.mohaberabi.jetmart.core.domain.model.JetMartPrefs
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import kotlinx.coroutines.flow.Flow

interface JetMartPrefsRepository {
    fun getJetMartPrefs(): Flow<JetMartPrefs>
    suspend fun sawOnBoarding(): EmptyDataResult<DataError.Local>
}