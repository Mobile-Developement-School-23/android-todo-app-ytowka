package com.danilkha.yandextodo.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.danilkha.yandextodo.data.local.database.TaskDao
import com.danilkha.yandextodo.data.local.entity.TaskEntity
import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.danilkha.yandextodo.domain.models.toEntity

interface TaskLocalDatasource {

    suspend fun getAllTasks(): List<TodoItemDto>
    suspend fun cacheTasks(version: Int, tasks: List<TodoItemDto>)
    suspend fun getTaskById(id: String): TodoItemDto
    suspend fun insertTask(todoItemDto: TodoItemDto)
    suspend fun getCacheVersion(): Int
    suspend fun updateCacheVersion(version: Int)

    suspend fun deleteTask(id: String)

    class Impl(
        private val taskDao: TaskDao,
        private val sharedPreferences: SharedPreferences,
    ) : TaskLocalDatasource{
        override suspend fun getAllTasks(): List<TodoItemDto> {
            return taskDao.getAllTasks().map(TaskEntity::toDto)
        }

        override suspend fun cacheTasks(version: Int, tasks: List<TodoItemDto>) {
            taskDao.updateCache(tasks.map(TodoItemDto::toEntity))
            updateCacheVersion(version)
        }

        override suspend fun getTaskById(id: String): TodoItemDto {
            return taskDao.getTaskById(id).toDto()
        }

        override suspend fun insertTask(todoItemDto: TodoItemDto) {
            taskDao.insertTask(todoItemDto.toEntity())
        }

        override suspend fun getCacheVersion(): Int {
            return sharedPreferences.getInt(CACHE_VERSION, 0)
        }

        override suspend fun updateCacheVersion(version: Int) {
            sharedPreferences.edit(commit = true) {
                putInt(CACHE_VERSION, version)
            }
        }

        override suspend fun deleteTask(id: String) {
            taskDao.deleteTask(id)
        }

    }

    companion object{
        private val CACHE_VERSION = "task_cache_version"
    }
}