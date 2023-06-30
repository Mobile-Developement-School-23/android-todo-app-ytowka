package com.danilkha.yandextodo.domain.models

import com.danilkha.yandextodo.data.local.entity.ImportanceDB
import com.danilkha.yandextodo.data.local.entity.TaskEntity
import com.danilkha.yandextodo.data.network.response.TaskResponse
import com.danilkha.yandextodo.domain.models.ImportanceDto.*
import com.danilkha.yandextodo.ui.models.Importance
import com.danilkha.yandextodo.ui.models.TodoItem
import java.util.Date

data class TodoItemDto(
    var id: String,
    val text: String,
    val importance: ImportanceDto,
    val time: Date?,
    val completed: Boolean,
    val createdAt: Date,
    val updatedAt: Date,
)

enum class ImportanceDto{
    LOW, NORMAL, HIGH;

    override fun toString() = when(this){
        LOW -> "low"
        NORMAL -> "basic"
        HIGH -> "important"
    }
}

fun ImportanceDto.toModel(): Importance = when(this){
    LOW -> Importance.LOW
    NORMAL -> Importance.NORMAL
    HIGH -> Importance.HIGH
}

fun ImportanceDto.toEntity(): ImportanceDB = when(this){
    LOW -> ImportanceDB.LOW
    NORMAL -> ImportanceDB.BASIC
    HIGH -> ImportanceDB.HIGH
}

fun TodoItemDto.toRequest(deviceId: String): TaskResponse = TaskResponse(
    id = id,
    text = text,
    importance = importance.toString(),
    deadline = time?.time,
    done = completed,
    color = null,
    createdAt = createdAt.time,
    changedAt = updatedAt.time,
    lastUpdatedBy = deviceId,
)
fun TodoItemDto.toModel(): TodoItem = TodoItem(
    id, text, importance.toModel(), time, completed, createdAt, updatedAt
)

fun TodoItemDto.toEntity(): TaskEntity = TaskEntity(
    id, text, importance.toEntity(), time?.time, completed, null, createdAt.time, updatedAt.time
)