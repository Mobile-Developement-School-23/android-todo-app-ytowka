package com.danilkha.yandextodo.ui.editor

import com.danilkha.yandextodo.ui.models.Importance
import com.danilkha.yandextodo.ui.models.TodoItem
import java.util.Date

data class TaskEditorState(
    val task: TodoItem,
    val isEditorMode: Boolean,
) {

    val isValid: Boolean by lazy {
        task.text.isNotBlank()
    }
}

sealed interface TaskEditorEvent

sealed interface TaskEditorUserEvent : TaskEditorEvent{

    class SetEditingTask(val todoItem: TodoItem) : TaskEditorUserEvent
    class UpdateText(val text: String) : TaskEditorUserEvent
    class SetDate(
        val date: Date?
    ) : TaskEditorUserEvent
    class SetImportance(val importance: Importance) : TaskEditorUserEvent
    object Save : TaskEditorUserEvent
    object Delete : TaskEditorUserEvent
}

sealed interface TaskEditorSideEffect{
    object DataUpdated : TaskEditorSideEffect
    class Error(val throwable: Throwable) : TaskEditorSideEffect
}