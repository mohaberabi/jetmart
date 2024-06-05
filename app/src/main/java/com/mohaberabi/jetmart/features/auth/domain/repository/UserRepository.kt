package com.mohaberabi.jetmart.features.auth.domain.repository

import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.auth.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        lastname: String,
    ): EmptyDataResult<DataError>

    suspend fun login(
        email: String,
        password: String,
    ): EmptyDataResult<DataError>


    fun getUserData(): Flow<UserModel>

    suspend fun getUserId(): String?
    suspend fun updateUserData(user: UserModel): EmptyDataResult<DataError>
}