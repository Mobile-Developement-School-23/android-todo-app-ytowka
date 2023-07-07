package com.danilkha.yandextodo.domain.usecase.task

import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.usecase.UseCase
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) : UseCase<TodoItemDto, Unit>() {
    override suspend fun execute(params: TodoItemDto) {
        repository.updateTask(params)
    }
}