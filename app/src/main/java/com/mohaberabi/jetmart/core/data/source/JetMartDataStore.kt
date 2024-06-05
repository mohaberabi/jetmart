package com.mohaberabi.jetmart.core.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mohaberabi.jetmart.core.domain.model.JetMartPrefs
import com.mohaberabi.jetmart.core.domain.source.local.JeetMartPrefsLocalDataSource
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class JetMartDataStore(
    private val datastore: DataStore<Preferences>,
    private val dispatchers: DispatchersProvider
) : JeetMartPrefsLocalDataSource {

    companion object {
        private val ONBOARDING_KEY = booleanPreferencesKey("onBoardingKey")
    }


    override suspend fun sawOnBoarding() {
        withContext(dispatchers.io) {
            datastore.edit { prefs ->
                prefs[ONBOARDING_KEY] = true
            }
        }
    }

    override fun getJetMartPrefs(): Flow<JetMartPrefs> {
        return datastore.data.map { prefs ->
            val onBoarding = prefs[ONBOARDING_KEY] ?: false
            JetMartPrefs(
                seenOnBoarding = onBoarding
            )
        }.flowOn(dispatchers.io)
    }

    override suspend fun clear() {
        withContext(dispatchers.io) {
            datastore.edit { prefs ->
                prefs.clear()
            }
        }
    }

}