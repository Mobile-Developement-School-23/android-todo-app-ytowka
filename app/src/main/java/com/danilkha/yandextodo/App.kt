package com.danilkha.yandextodo

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.danilkha.yandextodo.di.MainModule
import com.danilkha.yandextodo.worker.TaskSyncWorker
import java.util.concurrent.TimeUnit

class App : Application(), Configuration.Provider{

    val mainModule by lazy { MainModule(this) }

    override fun onCreate() {
        super.onCreate()
        syncData()
    }

    private fun syncData(){
        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicRequest = PeriodicWorkRequestBuilder<TaskSyncWorker>(8, TimeUnit.HOURS)
            .setConstraints(workConstraints)
            .build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }


    val workerFactory by lazy {
        object : WorkerFactory(){
            override fun createWorker(
                appContext: Context,
                workerClassName: String,
                workerParameters: WorkerParameters
            ): ListenableWorker? {
                if(workerClassName == TaskSyncWorker::class.qualifiedName){
                    return TaskSyncWorker(appContext, workerParameters, mainModule.getAllTaskUseCase())
                }
                return null
            }
        }
    }
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    companion object{
        const val SYNC_WORK_NAME = "sync_work"
    }
}