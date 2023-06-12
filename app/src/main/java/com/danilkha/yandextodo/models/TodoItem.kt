package com.danilkha.yandextodo.models

import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val time: Date?,
    val completed: Boolean,
    val createdAt: Date,
    val updatedAt: Date,
)

enum class Importance{ LOW, NORMAL, HIGH }
