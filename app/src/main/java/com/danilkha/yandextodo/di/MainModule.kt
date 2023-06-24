package com.danilkha.yandextodo.di

import com.danilkha.yandextodo.App
import com.danilkha.yandextodo.data.local.TaskLocalDatasource
import com.danilkha.yandextodo.data.network.TaskApiDatasource
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.usecase.task.DeleteTaskUseCase
import com.danilkha.yandextodo.domain.usecase.task.GetAllTasksUseCase
import com.danilkha.yandextodo.domain.usecase.task.UpdateTaskCompeteUseCase
import com.danilkha.yandextodo.domain.usecase.task.UpdateTaskUseCase


// заготовка под di, а пока деваться некуда, пусть будет так
class MainModule(val app: App) {

    val networkModule: NetworkModule by lazy {
        NetworkModule(app)
    }

    val dbModule: DBModule by lazy {
        DBModule(app)
    }

    fun taskLocalDatasource() = TaskLocalDatasource.Impl(
        taskDao = dbModule.taskDao,
        sharedPreferences = dbModule.sharedPrefs
    )
    fun taskApiDatasource() = TaskApiDatasource.Impl(
        networkModule.getApi()
    )

    val repository: TodoItemsRepository by lazy {
        TodoItemsRepository.Impl(
            taskLocalDatasource(),
            taskApiDatasource()
        )
    }

    fun getAllTaskUseCase() = GetAllTasksUseCase(repository)
    fun deleteTaskUseCase() = DeleteTaskUseCase(repository)
    fun updateTaskCompeteUseCase() = UpdateTaskCompeteUseCase(repository)
    fun updateTaskUseCase() = UpdateTaskUseCase(repository)

}