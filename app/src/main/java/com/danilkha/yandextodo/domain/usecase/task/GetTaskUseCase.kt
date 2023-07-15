package com.danilkha.yandextodo.domain.usecase.task

import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.usecase.UseCase
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) : UseCase<String, TodoItemDto>() {
    override suspend fun execute(params: String): TodoItemDto {
        return repository.getTaskById(params).first() ?: throw Exception("no such task with $params id")
    }

}