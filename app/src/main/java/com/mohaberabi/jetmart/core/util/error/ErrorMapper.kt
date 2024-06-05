package com.mohaberabi.jetmart.core.util.error

import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.util.UiText

fun AppError.asUiText(): UiText {
    return when (this) {
        is DataError -> this.asUiText()
        else -> DataError.Network.UNKNOWN_ERROR.asUiText()
    }
}

fun DataError.asUiText(): UiText {

    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.disk_full)
        DataError.Local.IOException -> UiText.StringResource(R.string.fatal_error)
        DataError.Network.NO_NETWORK -> UiText.StringResource(R.string.no_netowrk)
        DataError.Network.CONFLICT -> UiText.StringResource(R.string.conflict)
        DataError.Network.SERIALIZATION_ERROR -> UiText.StringResource(R.string.serialize_error)
        DataError.Network.UNKNOWN_ERROR -> UiText.StringResource(R.string.unknown_error)
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(R.string.payload_too_large)
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.request_timeout)
        DataError.Network.TOO_MANY_REQUEST -> UiText.StringResource(R.string.too_many_request)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.server_error)
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.unAuthed)
        is DataError.CommonError -> this.exception.mapErrorToUiText()
        is DataError.Authentication -> this.asUiText()
        else -> UiText.StringResource(R.string.unknown_error)
    }
}


private fun DataError.Authentication.asUiText(): UiText {

    return when (this) {
        DataError.Authentication.UNKNOWN_ERROR -> UiText.StringResource(R.string.unknown_error)
        DataError.Authentication.INVALID_EMAIL -> UiText.StringResource(R.string.invalid_email)
        DataError.Authentication.USER_DISABLED -> UiText.StringResource(R.string.user_disabled)
        DataError.Authentication.USER_NOT_FOUND -> UiText.StringResource(R.string.user_not_found)
        DataError.Authentication.EMAIL_ALREADY_IN_USE -> UiText.StringResource(R.string.email_already_in_use)
        DataError.Authentication.WEAK_PASSWORD -> UiText.StringResource(R.string.weak_password)
        DataError.Authentication.INVALID_CREDENTIALS -> UiText.StringResource(R.string.invalid_credentials)
        DataError.Authentication.INVALID_CUSTOM_TOKEN -> UiText.StringResource(R.string.invalid_custom_token)
        DataError.Authentication.ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL -> UiText.StringResource(R.string.account_exist)
    }
}

fun Throwable.mapErrorToUiText(): UiText {
    return if (this.message == null) {
        DataError.Network.UNKNOWN_ERROR.asUiText()
    } else {
        UiText.DynamicString(this.message!!)
    }

}