package com.danilkha.yandextodo.domain.repository

import com.danilkha.yandextodo.data.local.TaskLocalDatasource
import com.danilkha.yandextodo.data.network.TaskApiDatasource
import com.danilkha.yandextodo.domain.models.ImportanceDto
import com.danilkha.yandextodo.domain.models.TodoItemDto
import java.util.Date
import java.util.UUID

interface TodoItemsRepository {

    // suspend на будущее
    suspend fun getAllTasks(): List<TodoItemDto>

    suspend fun getTaskById(id: String): TodoItemDto?

    suspend fun updateTask(task: TodoItemDto)

    suspend fun deleteTask(task: TodoItemDto)

    suspend fun updateCompletedState(id: String, completed: Boolean)

    class Impl(
        val taskLocalDatasource: TaskLocalDatasource,
        val taskApiDatasource: TaskApiDatasource,
    ) : TodoItemsRepository {
        override suspend fun getAllTasks(): List<TodoItemDto> {
            return taskApiDatasource.getAllTasks().second
        }

        override suspend fun getTaskById(id: String): TodoItemDto? {
            return null
        }

        override suspend fun updateTask(task: TodoItemDto) {

        }

        override suspend fun deleteTask(task: TodoItemDto) {

        }

        override suspend fun updateCompletedState(id: String, completed: Boolean) {

        }
    }

}