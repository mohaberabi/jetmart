package com.mohaberabi.jetmart.core.util.error

import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException


fun FirebaseAuthException.fromFirebaseAuthException(): DataError.Authentication {
    return when (this) {
        is FirebaseAuthEmailException -> {
            when (errorCode) {
                ErrorCodes.INVALID_EMAIL -> DataError.Authentication.INVALID_EMAIL
                ErrorCodes.USER_DISABLED -> DataError.Authentication.USER_DISABLED
                ErrorCodes.USER_NOT_FOUND -> DataError.Authentication.USER_NOT_FOUND
                ErrorCodes.EMAIL_ALREADY_IN_USE -> DataError.Authentication.EMAIL_ALREADY_IN_USE
                ErrorCodes.WEAK_PASSWORD -> DataError.Authentication.WEAK_PASSWORD
                else -> DataError.Authentication.UNKNOWN_ERROR
            }
        }

        is FirebaseAuthInvalidUserException -> {
            when (errorCode) {
                ErrorCodes.USER_DISABLED -> DataError.Authentication.USER_DISABLED
                ErrorCodes.USER_NOT_FOUND -> DataError.Authentication.USER_NOT_FOUND
                else -> DataError.Authentication.UNKNOWN_ERROR
            }
        }

        is FirebaseAuthInvalidCredentialsException -> {
            when (errorCode) {
                ErrorCodes.WRONG_PASSWORD -> DataError.Authentication.INVALID_CREDENTIALS
                ErrorCodes.INVALID_CUSTOM_TOKEN -> DataError.Authentication.INVALID_CUSTOM_TOKEN
                else -> DataError.Authentication.UNKNOWN_ERROR
            }
        }

        is FirebaseAuthUserCollisionException -> {
            when (errorCode) {
                ErrorCodes.EMAIL_ALREADY_IN_USE -> DataError.Authentication.EMAIL_ALREADY_IN_USE
                ErrorCodes.ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL -> DataError.Authentication.ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL
                else -> DataError.Authentication.UNKNOWN_ERROR
            }
        }

        else -> DataError.Authentication.UNKNOWN_ERROR
    }
}