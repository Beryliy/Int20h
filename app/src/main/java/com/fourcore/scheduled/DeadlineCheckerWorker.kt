package com.fourcore.scheduled

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope

class DeadlineCheckerWorker(
    context: Context,
    workerParms: WorkerParameters,
    challengeId: String
): CoroutineWorker(context, workerParms) {

    override suspend fun doWork(): Result = coroutineScope {
        Result.success()
    }
}