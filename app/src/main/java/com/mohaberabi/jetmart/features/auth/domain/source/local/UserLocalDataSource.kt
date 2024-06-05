package com.mohaberabi.jetmart.features.auth.domain.source.local

import com.mohaberabi.jetmart.features.auth.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getUserData(): Flow<UserModel>
    suspend fun setUserData(user: UserModel)
    suspend fun clearUserData()
    suspend fun getUserId(): String?
}