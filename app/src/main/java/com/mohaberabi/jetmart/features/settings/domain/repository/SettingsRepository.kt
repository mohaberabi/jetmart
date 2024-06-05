package com.mohaberabi.jetmart.features.settings.domain.repository

interface SettingsRepository {
    suspend fun signOut()
}