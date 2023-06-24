package com.danilkha.yandextodo.domain

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

    class Impl() : TodoItemsRepository{

        private val tasks = mutableListOf(TodoItemDto(
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
                id = "5",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "6",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "7",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "8",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "9",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "10",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "11",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "12",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "13",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "14",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
            TodoItemDto(
                id = "15",
                text = "task 0",
                importance = ImportanceDto.NORMAL,
                time = null,
                completed = false,
                createdAt = Date(),
                updatedAt = Date()
            ),
        )
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