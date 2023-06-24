package com.danilkha.yandextodo.domain.usecase.task

import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.usecase.SimpleUseCase

class GetAllTasksUseCase(
    private val repository: TodoItemsRepository
) : SimpleUseCase<List<TodoItemDto>>() {
    override suspend fun execute(): List<TodoItemDto> {
       return repository.getAllTasks()
    }
}