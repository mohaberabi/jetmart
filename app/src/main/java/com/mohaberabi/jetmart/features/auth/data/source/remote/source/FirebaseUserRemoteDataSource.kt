package com.mohaberabi.jetmart.features.auth.data.source.remote.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.mohaberabi.jetmart.core.util.const.EndPoints
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.core.util.error.fromFirebaseAuthException
import com.mohaberabi.jetmart.core.util.error.fromFirebaseFirestoreException
import com.mohaberabi.jetmart.features.auth.data.source.remote.dto.UserDto
import com.mohaberabi.jetmart.features.auth.data.source.remote.dto.toUserModel
import com.mohaberabi.jetmart.features.auth.domain.model.UserModel
import com.mohaberabi.jetmart.features.auth.domain.source.remote.UserRemoteDataSource
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class FirebaseUserRemoteDataSource(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRemoteDataSource {
    override suspend fun login(
        email: String,
        password: String
    ): AppResult<UserModel, DataError> {

        return try {
            val authRes = auth.signInWithEmailAndPassword(email, password).await()
            authRes.user?.let { firebaseUser ->
                val uid = firebaseUser.uid
                val userDoc = firestore.collection(EndPoints.USERS).document(uid).get().await()
                val userData = userDoc.data
                if (userData != null) {
                    val user = userDoc.toObject<UserDto>()!!.toUserModel()
                    return@let AppResult.Done(user)
                } else {
                    return@let AppResult.Error(DataError.Authentication.USER_NOT_FOUND)
                }
            } ?: kotlin.run {
                AppResult.Error(DataError.Authentication.USER_NOT_FOUND)
            }
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseAuthException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Authentication.UNKNOWN_ERROR)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        lastName: String
    ): AppResult<UserModel, DataError> {
        return try {
            val userRes = auth.createUserWithEmailAndPassword(email, password).await()
            val userDto = UserDto(
                name = name,
                lastname = lastName,
                email = email,
                uid = userRes.user!!.uid
            )
            firestore.collection(EndPoints.USERS).document(userDto.uid).set(userDto)
            AppResult.Done(userDto.toUserModel())
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseAuthException())
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Authentication.UNKNOWN_ERROR)
        }
    }

    override suspend fun update(
        user: UserModel,
    ): EmptyDataResult<DataError> {
        return try {
            val dto = UserDto.fromDomain(user)
            firestore.collection(EndPoints.USERS).document(dto.uid).set(dto).await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Authentication.UNKNOWN_ERROR)
        }
    }

    override suspend fun signOut() {
        try {
            auth.signOut()
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
        }
    }

}