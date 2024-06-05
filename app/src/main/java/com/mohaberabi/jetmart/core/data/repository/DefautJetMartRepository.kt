package com.mohaberabi.jetmart.core.data.repository

import com.mohaberabi.jetmart.core.domain.model.JetMartPrefs
import com.mohaberabi.jetmart.core.domain.repository.JetMartPrefsRepository
import com.mohaberabi.jetmart.core.domain.source.local.JeetMartPrefsLocalDataSource
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import kotlinx.coroutines.flow.Flow
import java.io.IOException


class DefautJetMartRepository(
    private val jetMartLocalDataSource: JeetMartPrefsLocalDataSource
) : JetMartPrefsRepository {
    override fun getJetMartPrefs(): Flow<JetMartPrefs> =
        jetMartLocalDataSource.getJetMartPrefs()

    override suspend fun sawOnBoarding(): EmptyDataResult<DataError.Local> {
        return try {
            jetMartLocalDataSource.sawOnBoarding()
            AppResult.Done(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.IOException)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }

    }
}