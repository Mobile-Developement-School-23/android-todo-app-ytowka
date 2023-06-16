package com.danilkha.yandextodo.domain

import com.danilkha.yandextodo.domain.models.ImportanceDto
import com.danilkha.yandextodo.domain.models.TodoItemDto
import java.util.Date

interface TodoItemsRepository {

    suspend fun getAllTasks(): List<TodoItemDto>

    suspend fun getTaskById(id: String): TodoItemDto

    suspend fun updateTask(task: TodoItemDto)

    suspend fun deleteTask(task: TodoItemDto)

    class Impl() : TodoItemsRepository{
        override suspend fun getAllTasks(): List<TodoItemDto> {
            return listOf(
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "1",
                    text = "task important",
                    importance = ImportanceDto.HIGH,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

                TodoItemDto(
                    id = "2",
                    text = "task completed",
                    importance = ImportanceDto.HIGH,
                    time = null,
                    completed = true,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

                TodoItemDto(
                    id = "3",
                    text = "task not important",
                    importance = ImportanceDto.LOW,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

                TodoItemDto(
                    id = "4",
                    text = "task looooong fgjghsjkdghajkjfl;agdfgjkshlfgsdfg nklsjhdgsnfgh shskd klsdjfv lbsdjfhg kjhsfdjg hsdfghjsdfh jshd jghsdkfh gsh nghsdf ghsdj gjksdhj ghsdj ghsfhg",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItemDto(
                    id = "0",
                    text = "task 0",
                    importance = ImportanceDto.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

            )
        }

        override suspend fun getTaskById(id: String): TodoItemDto {
            return TodoItemDto(
                id = "0",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            )
        }

        override suspend fun updateTask(task: TodoItemDto) {

        }

        override suspend fun deleteTask(task: TodoItemDto) {

        }
    }

}