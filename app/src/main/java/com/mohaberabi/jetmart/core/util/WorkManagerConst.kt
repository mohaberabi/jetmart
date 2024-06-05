package com.mohaberabi.jetmart.core.util

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mohaberabi.jetmart.features.cart.data.workers.FetchCartWorker
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration
import androidx.work.PeriodicWorkRequestBuilder


object WorkManagerConst {
    const val UID_WORKER_INPUT_DATA = "userId"
    const val MAX_ALLOWED_ATTEMPTS = 5
    val RETRY_POLICY = BackoffPolicy.EXPONENTIAL
    const val BACK_OFF_DELAY = 2000L
    val BACK_OFF_TIME_UNIT = TimeUnit.MILLISECONDS
    val baseConstraints: Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()
}

inline fun <reified T : ListenableWorker> WorkManager.jetMartPeriodicWorkerRequest(
    tag: String,
    inputs: Data.Builder = Data.Builder(),
    interval: Duration
): PeriodicWorkRequest.Builder {
    val request = PeriodicWorkRequestBuilder<FetchCartWorker>(
        repeatInterval = interval.toJavaDuration()
    ).setConstraints(WorkManagerConst.baseConstraints)
        .setBackoffCriteria(
            backoffPolicy = WorkManagerConst.RETRY_POLICY,
            backoffDelay = WorkManagerConst.BACK_OFF_DELAY,
            timeUnit = WorkManagerConst.BACK_OFF_TIME_UNIT
        )
        .setInitialDelay(
            duration = 30,
            timeUnit = TimeUnit.MINUTES
        ).setInputData(
            inputs.build()
        ).addTag(tag)
    return request
}

inline fun <reified T : ListenableWorker> WorkManager.jetMartOneTimeWorkerRequest(
    tag: String,
    inputs: Data.Builder,
): OneTimeWorkRequest.Builder {
    val request = OneTimeWorkRequestBuilder<T>()
        .addTag(tag)
        .setConstraints(WorkManagerConst.baseConstraints)
        .setBackoffCriteria(
            backoffPolicy = WorkManagerConst.RETRY_POLICY,
            backoffDelay = WorkManagerConst.BACK_OFF_DELAY,
            timeUnit = WorkManagerConst.BACK_OFF_TIME_UNIT
        ).setInputData(
            inputs.build()
        )

    return request
}