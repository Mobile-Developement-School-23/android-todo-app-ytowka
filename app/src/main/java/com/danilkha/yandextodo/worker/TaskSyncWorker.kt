package com.danilkha.yandextodo.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.danilkha.yandextodo.domain.usecase.task.GetAllTasksUseCase
import kotlinx.coroutines.flow.last
import java.net.UnknownHostException

class TaskSyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    val getAllTasksUseCase: GetAllTasksUseCase
) : CoroutineWorker(
   context, workerParams
) {
    override suspend fun doWork(): Result {
        val updateResult = getAllTasksUseCase().last()
        return if(updateResult.isFailure){
            if(updateResult.exceptionOrNull() is UnknownHostException){
                Result.retry()
            }else{
                Result.failure()
            }
        }else{
            Result.success()
        }
    }
}