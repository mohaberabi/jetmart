package com.mohaberabi.jetmart.core.util.error

import com.google.firebase.firestore.FirebaseFirestoreException

fun FirebaseFirestoreException.fromFirebaseFirestoreException(): DataError {
    return when (this.code) {
        FirebaseFirestoreException.Code.CANCELLED -> DataError.Network.CANCELED
        FirebaseFirestoreException.Code.UNKNOWN -> DataError.Network.UNKNOWN_ERROR
        FirebaseFirestoreException.Code.INVALID_ARGUMENT -> DataError.Network.INVALID_ARGUMENT
        FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> DataError.Network.DEADLINE_EXCEEDED
        FirebaseFirestoreException.Code.NOT_FOUND -> DataError.Network.UNKNOWN_ERROR
        FirebaseFirestoreException.Code.ALREADY_EXISTS -> DataError.Network.CONFLICT
        FirebaseFirestoreException.Code.PERMISSION_DENIED -> DataError.Network.PERMISSION_DENIED
        FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED -> DataError.Network.RESOURCE_EXHAUSTED
        FirebaseFirestoreException.Code.FAILED_PRECONDITION -> DataError.Network.FAILED_PRECONDITION
        FirebaseFirestoreException.Code.ABORTED -> DataError.Network.ABORTED
        FirebaseFirestoreException.Code.OUT_OF_RANGE -> DataError.Network.OUT_OF_RANGE
        FirebaseFirestoreException.Code.UNIMPLEMENTED -> DataError.Network.UNIMPLEMENTED
        FirebaseFirestoreException.Code.INTERNAL -> DataError.Network.SERVER_ERROR
        FirebaseFirestoreException.Code.UNAVAILABLE -> DataError.Network.UNAVAILABLE
        FirebaseFirestoreException.Code.DATA_LOSS -> DataError.Network.DATA_LOSS
        FirebaseFirestoreException.Code.UNAUTHENTICATED -> DataError.Network.UNAUTHORIZED
        else -> DataError.Network.UNKNOWN_ERROR
    }
}