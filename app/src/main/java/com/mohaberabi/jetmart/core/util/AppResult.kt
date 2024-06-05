package com.mohaberabi.jetmart.core.util

import com.mohaberabi.jetmart.core.util.error.AppError


sealed interface AppResult<out D, out E : AppError> {
    data class Done<out D>(val data: D) : AppResult<D, Nothing>
    data class Error<out E : AppError>(val error: E) : AppResult<Nothing, E>

}


inline fun <T, E : AppError, R> AppResult<T, E>.map(map: (T) -> R): AppResult<R, E> {
    return when (this) {
        is AppResult.Error -> AppResult.Error(error)
        is AppResult.Done -> AppResult.Done(map(data))
    }
}

inline fun <T, E : AppError> AppResult<T, E>.fold(
    whenDone: (T) -> Unit = {},
    whenError: (E) -> Unit = {}
) {
    when (this) {
        is AppResult.Error -> whenError(this.error)
        is AppResult.Done -> whenDone(this.data)
    }
}

inline fun <T, E : AppError> AppResult<T, E>.foldAndReturn(
    whenDone: (T) -> Unit,
    whenError: (E) -> Unit
): AppResult<T, E> {
    return when (this) {
        is AppResult.Error -> {
            whenError(this.error)
            AppResult.Error(this.error)
        }

        is AppResult.Done -> {
            whenDone(this.data)
            AppResult.Done(this.data)
        }
    }
}

inline fun <T, E : AppError> AppResult<T, E>.foldWithResult(
    whenDone: (T) -> AppResult<T, Nothing>,
    whenError: (E) -> AppResult<Nothing, E>
): AppResult<T, E> {
    return when (this) {
        is AppResult.Error -> whenError(this.error)
        is AppResult.Done -> whenDone(this.data)
    }
}


fun <T, E : AppError> AppResult<T, E>.asEmptyResult(): EmptyDataResult<E> {
    return map { Unit }
}
typealias EmptyDataResult<E> = AppResult<Unit, E>


