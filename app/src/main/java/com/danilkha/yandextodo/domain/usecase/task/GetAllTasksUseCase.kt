package com.danilkha.yandextodo.domain.usecase.task

import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.usecase.SimpleFlowUseCase
import com.danilkha.yandextodo.domain.usecase.SimpleUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) : SimpleFlowUseCase<List<TodoItemDto>>() {
    override fun execute(): Flow<List<TodoItemDto>> = repository.getAllTasks()

}