package com.danilkha.yandextodo.ui.list

import com.danilkha.yandextodo.ui.models.TodoItem

data class TodoListState(
    val tasks: List<TodoItem>,
    val showCompleted: Boolean = true,
){
    val resultTasks by lazy {
        if(showCompleted) tasks
        else tasks.filter {
            !it.completed
        }
    }

    val completed by lazy {
        tasks.count { it.completed }
    }
}

sealed interface TodoListEvent{

    class TaskLoaded(val tasks: List<TodoItem>) : TodoListEvent
}

sealed interface TodoListUserEvent : TodoListEvent{
    class UpdateCheckedState(val id: String, val isChecked: Boolean) : TodoListUserEvent

    object ToggleCompletedTasks : TodoListUserEvent
    object UpdateData : TodoListUserEvent
}

sealed interface TodoListSideEffect