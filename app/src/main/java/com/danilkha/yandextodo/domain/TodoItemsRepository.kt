package com.danilkha.yandextodo.domain

import com.danilkha.yandextodo.models.Importance
import com.danilkha.yandextodo.models.TodoItem
import java.util.Calendar
import java.util.Date

interface TodoItemsRepository {

    suspend fun getAllTasks(): List<TodoItem>

    class Impl() : TodoItemsRepository{
        override suspend fun getAllTasks(): List<TodoItem> {
            return listOf(
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "1",
                    text = "task important",
                    importance = Importance.HIGH,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

                TodoItem(
                    id = "2",
                    text = "task completed",
                    importance = Importance.HIGH,
                    time = null,
                    completed = true,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

                TodoItem(
                    id = "3",
                    text = "task not important",
                    importance = Importance.LOW,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

                TodoItem(
                    id = "4",
                    text = "task looooong fgjghsjkdghajkjfl;agdfgjkshlfgsdfg nklsjhdgsnfgh shskd klsdjfv lbsdjfhg kjhsfdjg hsdfghjsdfh jshd jghsdkfh gsh nghsdf ghsdj gjksdhj ghsdj ghsfhg",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),
                TodoItem(
                    id = "0",
                    text = "task 0",
                    importance = Importance.NORMAL,
                    time = null,
                    completed = false,
                    createdAt = Date(),
                    updatedAt = Date()
                ),

            )
        }
    }

}