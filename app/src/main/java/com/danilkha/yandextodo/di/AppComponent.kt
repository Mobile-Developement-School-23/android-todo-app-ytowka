package com.danilkha.yandextodo.di

import com.danilkha.yandextodo.App
import com.danilkha.yandextodo.ui.editor.TaskEditorViewModel
import com.danilkha.yandextodo.ui.list.TodoListViewModel
import com.danilkha.yandextodo.worker.TaskSyncWorker
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component(modules = [
    RepositoryModule::class, NetworkModule::class, DBModule::class, DatasourceModule::class, MainModule::class
])
@AppScope
abstract class AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app: App, @BindsInstance deviceId: App.DeviceId): AppComponent
    }

    @AppScope
    abstract val viewModelComponent: ViewModelComponent

    abstract val taskSyncWorker: TaskSyncWorker.Factory
}

@Scope
annotation class AppScope