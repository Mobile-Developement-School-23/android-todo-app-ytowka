package com.danilkha.yandextodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danilkha.yandextodo.domain.models.TodoItemDto
import java.util.Date

@Entity(tableName = TaskEntity.TABLE_NAME)
data class TaskEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = TASK_ID) val id: String,
    @ColumnInfo(name = TEXT) val text: String,
    @ColumnInfo(name = IMPORTANCE) val importance: ImportanceDB,
    @ColumnInfo(name = DEADLINE) val deadline: Long?,
    @ColumnInfo(name = DONE) val done: Boolean,
    @ColumnInfo(name = COLOR) val color: String?,
    @ColumnInfo(name = CREATED_AT) val createdAt: Long,
    @ColumnInfo(name = CHANGED_AY) val changedAt: Long,
) {

    data class Done(
        @ColumnInfo(name = TASK_ID) val id: String,
        @ColumnInfo(name = DONE) val done: Boolean,
    )

    companion object{
        const val TABLE_NAME = "tasks"

        const val TASK_ID = "id"
        const val TEXT = "text"
        const val IMPORTANCE = "importance"
        const val DEADLINE = "deadline"
        const val DONE = "done"
        const val COLOR = "color"
        const val CREATED_AT = "created_at"
        const val CHANGED_AY = "changed_at"
    }

    fun toDto(): TodoItemDto = TodoItemDto(
        id = id,
        text = text,
        importance = importance.toDto(),
        time = deadline?.let { Date(it) },
        completed = done,
        createdAt = Date(createdAt),
        updatedAt = Date(createdAt)
    )
}