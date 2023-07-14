package com.danilkha.yandextodo.domain.usecase.task

import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.usecase.UseCase
import javax.inject.Inject

class UpdateTaskCompeteUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) : UseCase<UpdateTaskCompeteUseCase.Params, Unit>() {
    override suspend fun execute(params: Params) {
        repository.updateCompletedState(params.id, params.completed)
    }

    class Params(
        val id: String, val completed: Boolean
    )
}