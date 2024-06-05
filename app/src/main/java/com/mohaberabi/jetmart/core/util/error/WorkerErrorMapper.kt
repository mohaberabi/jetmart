package com.mohaberabi.jetmart.core.util.error

import androidx.work.ListenableWorker


fun DataError.toWorkerResult(): ListenableWorker.Result {

    return when (this) {
        DataError.Local.DISK_FULL -> ListenableWorker.Result.failure()
        DataError.Local.UNKNOWN -> ListenableWorker.Result.failure()
        DataError.Network.REQUEST_TIMEOUT -> ListenableWorker.Result.retry()
        DataError.Network.UNAUTHORIZED -> ListenableWorker.Result.failure()
        DataError.Network.CONFLICT -> ListenableWorker.Result.failure()
        DataError.Network.TOO_MANY_REQUEST -> ListenableWorker.Result.retry()
        DataError.Network.NO_NETWORK -> ListenableWorker.Result.retry()
        DataError.Network.PAYLOAD_TOO_LARGE -> ListenableWorker.Result.failure()
        DataError.Network.SERVER_ERROR -> ListenableWorker.Result.retry()
        DataError.Network.SERIALIZATION_ERROR -> ListenableWorker.Result.failure()
        DataError.Network.UNKNOWN_ERROR -> ListenableWorker.Result.retry()
        DataError.Local.IOException -> ListenableWorker.Result.failure()
        DataError.Network.CANCELED -> ListenableWorker.Result.retry()
        DataError.Network.INVALID_ARGUMENT -> ListenableWorker.Result.failure()
        DataError.Network.DEADLINE_EXCEEDED -> ListenableWorker.Result.failure()
        DataError.Network.ABORTED -> ListenableWorker.Result.failure()
        DataError.Network.FAILED_PRECONDITION -> ListenableWorker.Result.failure()
        DataError.Network.RESOURCE_EXHAUSTED -> ListenableWorker.Result.failure()
        DataError.Network.PERMISSION_DENIED -> ListenableWorker.Result.failure()
        DataError.Network.UNAVAILABLE -> ListenableWorker.Result.failure()
        DataError.Network.UNIMPLEMENTED -> ListenableWorker.Result.failure()
        DataError.Network.OUT_OF_RANGE -> ListenableWorker.Result.failure()
        DataError.Network.DATA_LOSS -> ListenableWorker.Result.failure()
        else -> ListenableWorker.Result.failure()
    }


}