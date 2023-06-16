package com.danilkha.yandextodo.ui.list

import com.danilkha.yandextodo.ui.models.TodoItem

data class TodoListState(
    val tasks: List<TodoItem>,
)

sealed interface TodoListEvent{

    class TaskLoaded(val tasks: List<TodoItem>) : TodoListEvent
}

sealed interface TodoListUserEvent : TodoListEvent{
    class UpdateCheckedState(val index: Int, val isChecked: Boolean) : TodoListUserEvent
}

sealed interface TodoListSideEffect