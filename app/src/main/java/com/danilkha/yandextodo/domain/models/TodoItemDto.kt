package com.danilkha.yandextodo.domain.models

import com.danilkha.yandextodo.domain.models.ImportanceDto.*
import com.danilkha.yandextodo.ui.models.Importance
import com.danilkha.yandextodo.ui.models.TodoItem
import java.util.Date

data class TodoItemDto(
    val id: String,
    val text: String,
    val importance: ImportanceDto,
    val time: Date?,
    val completed: Boolean,
    val createdAt: Date,
    val updatedAt: Date,
)

enum class ImportanceDto{ LOW, NORMAL, HIGH }

fun ImportanceDto.toModel(): Importance = when(this){
    LOW -> Importance.LOW
    NORMAL -> Importance.NORMAL
    HIGH -> Importance.HIGH
}

fun TodoItemDto.toModel(): TodoItem = TodoItem(
    id, text, importance.toModel(), time, completed, createdAt, updatedAt
)