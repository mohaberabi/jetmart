package com.mohaberabi.jetmart.core.util.extensions

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.AppError
import com.mohaberabi.jetmart.core.util.error.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.asResult(

): Flow<AppResult<T, AppError>> = map<T, AppResult<T, AppError>> {
    AppResult.Done(it)
}.catch {
    emit(AppResult.Error(DataError.CommonError(it)))
}