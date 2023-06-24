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
        taskLocalDatasource: TaskLocalDatasource,
        taskApiDatasource: TaskApiDatasource,
    ) : TodoItemsRepository {

        private val tasks = mutableListOf<TodoItemDto>()
        override suspend fun getAllTasks(): List<TodoItemDto> {
            return tasks
        }

        override suspend fun getTaskById(id: String): TodoItemDto? {
            return tasks.find { it.id == id }
        }

        override suspend fun updateTask(task: TodoItemDto) {
            val index = tasks.indexOfFirst { it.id == task.id }
            val date = Date()
            var updatedTask = task.copy(
                updatedAt = date
            )
            if(index != -1){
                tasks[index] = updatedTask
            }else{
                updatedTask = updatedTask.copy(
                    createdAt = date,
                    id = UUID.randomUUID().toString()
                )
                tasks.add(updatedTask)
            }
        }

        override suspend fun deleteTask(task: TodoItemDto) {
            tasks.removeIf { it.id == task.id }
        }

        override suspend fun updateCompletedState(id: String, completed: Boolean) {
            val index = tasks.indexOfFirst { it.id == id }
            val task = tasks[index].copy(
                completed = completed
            )
            tasks[index] = task
        }
    }

}