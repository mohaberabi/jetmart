package com.mohaberabi.jetmart.features.auth.data.repository

import com.mohaberabi.jetmart.core.domain.source.local.JeetMartPrefsLocalDataSource
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.auth.domain.model.UserModel
import com.mohaberabi.jetmart.features.auth.domain.repository.UserRepository
import com.mohaberabi.jetmart.features.auth.domain.source.local.UserLocalDataSource
import com.mohaberabi.jetmart.features.auth.domain.source.remote.UserRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class DefaultUserRepository(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val jetMartLocalDataSource: JeetMartPrefsLocalDataSource
) : UserRepository {
    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        lastname: String
    ): EmptyDataResult<DataError> {

        val res = userRemoteDataSource.signUp(
            email = email,
            password = password,
            lastName = lastname,
            name = name
        )

        return when (res) {
            is AppResult.Done -> {
                userLocalDataSource.setUserData(res.data)
                AppResult.Done(Unit)
            }

            is AppResult.Error -> res
        }
    }

    override suspend fun login(email: String, password: String): EmptyDataResult<DataError> {
        val res = userRemoteDataSource.login(
            email = email,
            password = password
        )
        return when (res) {
            is AppResult.Done -> {
                userLocalDataSource.setUserData(res.data)
                jetMartLocalDataSource.sawOnBoarding()
                AppResult.Done(Unit)
            }

            is AppResult.Error -> res
        }
    }


    override fun getUserData(): Flow<UserModel> = userLocalDataSource.getUserData()
    override suspend fun getUserId(): String? = userLocalDataSource.getUserId()

    override suspend fun updateUserData(user: UserModel): EmptyDataResult<DataError> {
        val res = userRemoteDataSource.update(user)
        if (res is AppResult.Done) {
            userLocalDataSource.setUserData(user)
        }
        return res
    }
}