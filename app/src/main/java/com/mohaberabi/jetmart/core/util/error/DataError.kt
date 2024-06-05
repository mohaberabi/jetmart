package com.mohaberabi.jetmart.core.util.error

sealed interface DataError : AppError {

    data class CommonError(
        val exception: Throwable,
    ) : DataError

    enum class Network : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUEST,
        NO_NETWORK,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        CANCELED,
        INVALID_ARGUMENT,
        DEADLINE_EXCEEDED,
        ABORTED,
        FAILED_PRECONDITION,
        RESOURCE_EXHAUSTED,
        PERMISSION_DENIED,
        UNAVAILABLE,
        UNIMPLEMENTED,
        OUT_OF_RANGE,
        DATA_LOSS,
        UNKNOWN_ERROR
    }

    enum class Authentication : DataError {

        UNKNOWN_ERROR,
        INVALID_EMAIL,
        USER_DISABLED,
        USER_NOT_FOUND,
        EMAIL_ALREADY_IN_USE,
        WEAK_PASSWORD,
        INVALID_CREDENTIALS,
        INVALID_CUSTOM_TOKEN,
        ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL

    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN,
        IOException,
    }

}

