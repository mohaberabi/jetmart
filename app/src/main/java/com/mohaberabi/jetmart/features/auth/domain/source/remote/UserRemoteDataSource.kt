package com.mohaberabi.jetmart.features.auth.domain.source.remote

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.auth.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun login(
        email: String,
        password: String
    ): AppResult<UserModel, DataError>

    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        lastName: String,
    ): AppResult<UserModel, DataError>

    suspend fun update(user: UserModel): EmptyDataResult<DataError>
    suspend fun signOut()
}