package com.danilkha.yandextodo.data.network.response

import com.danilkha.yandextodo.domain.models.ImportanceDto
import com.danilkha.yandextodo.domain.models.TodoItemDto
import com.google.gson.annotations.SerializedName
import java.util.Date


/*
id": <uuid>, // уникальный идентификатор элемента
"text": "blablabla",
"importance": "<importance>", // importance = low | basic | important
"deadline": <unix timestamp>, // int64, может отсутствовать, тогда нет
"done": <bool>,
"color": "#FFFFFF", // может отсутствовать
"created_at": <unix timestamp>,
"changed_at": <unix timestamp>,
"last_updated_by": <device id>
*/
data class TaskResponse(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("importance") val importance: String,
    @SerializedName("deadline") val deadline: Long?,
    @SerializedName("done") val done: Boolean,
    @SerializedName("color") val color: String?,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("changed_at") val changedAt: Long,
    @SerializedName("last_updated_by") val lastUpdatedBy: String,
){

    fun toDto(): TodoItemDto = TodoItemDto(
        id = id,
        text = text,
        importance = when(importance){
            "low" -> ImportanceDto.LOW
            "basic" -> ImportanceDto.NORMAL
            "important" -> ImportanceDto.HIGH
            else -> ImportanceDto.NORMAL
        },
        time = deadline?.let { Date(it) },
        completed = done,
        createdAt = Date(createdAt),
        updatedAt = Date(changedAt)
    )
}
