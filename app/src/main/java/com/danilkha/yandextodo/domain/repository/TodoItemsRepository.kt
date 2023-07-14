package com.danilkha.yandextodo.domain.repository

import android.util.Log
import com.danilkha.yandextodo.data.local.TaskLocalDatasource
import com.danilkha.yandextodo.data.network.TaskApiDatasource
import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.danilkha.yandextodo.domain.models.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface TodoItemsRepository {

    // suspend на будущее
    fun getAllTasks(): Flow<List<TodoItemDto>>

    fun getTaskById(id: String): Flow<TodoItemDto?>

    suspend fun updateTask(task: TodoItemDto)

    suspend fun createTask(task: TodoItemDto)

    suspend fun deleteTask(task: TodoItemDto)

    suspend fun updateCompletedState(id: String, completed: Boolean)

    class Impl @Inject constructor(
        private val taskLocalDatasource: TaskLocalDatasource,
        private val taskApiDatasource: TaskApiDatasource,
    ) : TodoItemsRepository {
        override fun getAllTasks(): Flow<List<TodoItemDto>> = flow{
            val version = taskLocalDatasource.getCacheVersion()
            val localTasks = taskLocalDatasource.getAllTasks()
            emit(localTasks)
            val apiTasks = taskApiDatasource.getAllTasks(version)
            emit(apiTasks.second)
            taskLocalDatasource.cacheTasks(
                apiTasks.first,
                apiTasks.second
            )
        }

        override fun getTaskById(id: String): Flow<TodoItemDto?> = flow{
            val version = taskLocalDatasource.getCacheVersion()
            val task = taskApiDatasource.getTaskById(version, id)
            emit(task.second)
        }

        override suspend fun updateTask(task: TodoItemDto) {
            val version = taskLocalDatasource.getCacheVersion()
            val response = taskApiDatasource.updateTask(version, task)
            taskLocalDatasource.updateCacheVersion(response.first)
            taskLocalDatasource.insertTask(response.second)
        }

        override suspend fun createTask(task: TodoItemDto) {
            val version = taskLocalDatasource.getCacheVersion()
            val response = taskApiDatasource.createTask(version, task)
            taskLocalDatasource.updateCacheVersion(response.first)
            taskLocalDatasource.insertTask(response.second)
        }

        override suspend fun deleteTask(task: TodoItemDto) {
            val version = taskLocalDatasource.getCacheVersion()
            val response = taskApiDatasource.deleteTask(version, task.id)
            taskLocalDatasource.updateCacheVersion(response.first)
            taskLocalDatasource.deleteTask(task.id)
        }

        override suspend fun updateCompletedState(id: String, completed: Boolean) {
            val version = taskLocalDatasource.getCacheVersion()
            val task = taskLocalDatasource.getTaskById(id).copy(
                completed = completed
            )
            val response = taskApiDatasource.updateTask(version, task)
            taskLocalDatasource.insertTask(response.second)
            taskLocalDatasource.updateCacheVersion(response.first)
        }
    }

}