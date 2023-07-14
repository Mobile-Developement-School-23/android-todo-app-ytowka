package com.danilkha.yandextodo.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.danilkha.yandextodo.domain.usecase.task.GetAllTasksUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.last
import java.net.UnknownHostException

class TaskSyncWorker @AssistedInject constructor(
    context: Context,
    @Assisted workerParams: WorkerParameters,
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


    @AssistedFactory
    interface Factory{
        fun create(workerParameters: WorkerParameters): TaskSyncWorker
    }
}