package com.danilkha.yandextodo.domain.usecase.task

import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.usecase.UseCase
import java.util.UUID
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) : UseCase<TodoItemDto, Unit>() {
    override suspend fun execute(params: TodoItemDto) {
        params.id = UUID.randomUUID().toString()
        repository.createTask(params)
    }
}