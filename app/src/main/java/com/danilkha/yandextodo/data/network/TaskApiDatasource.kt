package com.danilkha.yandextodo.data.network

import com.danilkha.yandextodo.data.network.response.TaskResponse
import com.danilkha.yandextodo.domain.models.TodoItemDto

interface TaskApiDatasource {

    suspend fun getAllTasks(): Pair<Int, List<TodoItemDto>>
    class Impl(
        private val api: Api
    ) : TaskApiDatasource{

        override suspend fun getAllTasks(): Pair<Int, List<TodoItemDto>> {
            val response = api.getTaskList(0)

            return response.revision to response.list.map(TaskResponse::toDto)
        }

    }
}