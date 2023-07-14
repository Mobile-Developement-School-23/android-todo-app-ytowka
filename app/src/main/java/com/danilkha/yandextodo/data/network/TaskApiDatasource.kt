package com.danilkha.yandextodo.data.network

import com.danilkha.yandextodo.data.network.request.TaskListRequest
import com.danilkha.yandextodo.data.network.request.TaskRequest
import com.danilkha.yandextodo.data.network.response.TaskResponse
import com.danilkha.yandextodo.di.DatasourceModule
import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.danilkha.yandextodo.domain.models.toRequest
import javax.inject.Inject

interface TaskApiDatasource {
    suspend fun getAllTasks(version: Int): Pair<Int, List<TodoItemDto>>

    suspend fun getTaskById(version: Int, id: String): Pair<Int, TodoItemDto>
    suspend fun updateTask(version: Int, todoItemDto: TodoItemDto): Pair<Int, TodoItemDto>
    suspend fun updateTasks(version: Int, todoItemDto: List<TodoItemDto>): Pair<Int, List<TodoItemDto>>
    suspend fun createTask(version: Int, todoItemDto: TodoItemDto): Pair<Int, TodoItemDto>
    suspend fun deleteTask(version: Int, id: String): Pair<Int, TodoItemDto>


    class Impl @Inject constructor(
        private val api: Api,
        @DatasourceModule.DeviceId private val deviceId: String,
    ) : TaskApiDatasource{

        override suspend fun getAllTasks(version: Int): Pair<Int, List<TodoItemDto>> {
            val response = api.getTaskList(version)

            return response.revision to response.list.map(TaskResponse::toDto)
        }

        override suspend fun getTaskById(version: Int, id: String): Pair<Int, TodoItemDto> {
            val response = api.getTaskById(version, id)

            return response.revision to response.task.toDto()
        }

        override suspend fun updateTask(version: Int, todoItemDto: TodoItemDto): Pair<Int, TodoItemDto> {
            val taskRequest = todoItemDto.toRequest(deviceId)
            val response = api.updateTask(version, TaskRequest(taskRequest), todoItemDto.id)

            return response.revision to response.task.toDto()
        }

        override suspend fun updateTasks(version: Int, todoItemDto: List<TodoItemDto>): Pair<Int, List<TodoItemDto>> {
            val taskRequest = todoItemDto.map {
                it.toRequest(deviceId)
            }
            val response = api.updateTasks(version, TaskListRequest(taskRequest))

            return response.revision to response.list.map { it.toDto() }
        }

        override suspend fun createTask(version: Int, todoItemDto: TodoItemDto): Pair<Int, TodoItemDto> {
            val taskRequest = todoItemDto.toRequest(deviceId)
            val response = api.createTask(version, TaskRequest(taskRequest))

            return response.revision to response.task.toDto()
        }

        override suspend fun deleteTask(version: Int, id: String): Pair<Int, TodoItemDto> {
            val response = api.deleteTask(version, id)

            return response.revision to response.task.toDto()
        }

    }
}