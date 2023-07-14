package com.danilkha.yandextodo.di

import com.danilkha.yandextodo.App
import com.danilkha.yandextodo.data.local.TaskLocalDatasource
import com.danilkha.yandextodo.data.network.TaskApiDatasource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier


@Module
interface DatasourceModule {

    @Binds
    fun getTaskLocalDatasource(taskLocalDatasource: TaskLocalDatasource.Impl): TaskLocalDatasource

    @Binds
    fun getTaskApiDatasource(taskApiDatasource: TaskApiDatasource.Impl): TaskApiDatasource

    companion object{
        @Provides
        @DeviceId
        fun getDeviceId(deviceId: App.DeviceId): String{
            return deviceId.id
        }
    }

    @Qualifier
    annotation class DeviceId
}