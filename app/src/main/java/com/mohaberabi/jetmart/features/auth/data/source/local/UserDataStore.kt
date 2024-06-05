package com.mohaberabi.jetmart.features.auth.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.features.auth.domain.model.UserModel
import com.mohaberabi.jetmart.features.auth.domain.source.local.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserDataStore(
    private val dispatchers: DispatchersProvider,
    private val dataStore: DataStore<Preferences>
) : UserLocalDataSource {
    companion object {
        private val NAME_KEY = stringPreferencesKey("nameKey")
        private val LASTNAME_KEY = stringPreferencesKey("lastnameKey")
        private val EMAIL_KEY = stringPreferencesKey("emailKey")
        private val UID_KEY = stringPreferencesKey("uidKey")
        private val LANG_KEY = stringPreferencesKey("langKey")
        private val TOKEN_KEY = stringPreferencesKey("tokenKey")
    }

    override fun getUserData(): Flow<UserModel> {
        return dataStore.data.map { prefs ->
            val name = prefs[NAME_KEY] ?: ""
            val lastName = prefs[LASTNAME_KEY] ?: ""
            val email = prefs[EMAIL_KEY] ?: ""
            val uid = prefs[UID_KEY] ?: ""
            val lang = prefs[LANG_KEY] ?: ""
            val token = prefs[TOKEN_KEY] ?: ""
            UserModel(
                name = name,
                lastname = lastName,
                email = email,
                uid = uid,
                langCode = lang,
                token = token
            )
        }.flowOn(dispatchers.io)
    }

    override suspend fun getUserId(): String? {
        return dataStore.data.map {
            it[UID_KEY]
        }.flowOn(dispatchers.io).first()
    }

    override suspend fun setUserData(user: UserModel) {
        withContext(dispatchers.io) {
            dataStore.edit { prefs ->
                prefs[NAME_KEY] = user.name
                prefs[LANG_KEY] = user.langCode
                prefs[UID_KEY] = user.uid
                prefs[EMAIL_KEY] = user.email
                prefs[TOKEN_KEY] = user.token
                prefs[LASTNAME_KEY] = user.lastname
            }
        }
    }

    override suspend fun clearUserData() {
        withContext(dispatchers.io) {
            dataStore.edit { prefs ->
                prefs.clear()
            }
        }
    }
}