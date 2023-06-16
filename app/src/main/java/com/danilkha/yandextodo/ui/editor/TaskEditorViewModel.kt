package com.danilkha.yandextodo.ui.editor

import com.danilkha.yandextodo.domain.TodoItemsRepository
import com.danilkha.yandextodo.ui.models.Importance
import com.danilkha.yandextodo.ui.models.TodoItem
import com.danilkha.yandextodo.ui.models.toDto
import com.danilkha.yandextodo.ui.utils.MviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class TaskEditorViewModel constructor(
    private val taskRepository: TodoItemsRepository
): MviViewModel<TaskEditorState, TaskEditorEvent, TaskEditorUserEvent, TaskEditorSideEffect>(){

    override val startState: TaskEditorState = TaskEditorState(
        TodoItem("", "", Importance.NORMAL, time = null, completed = false, createdAt = Date(), updatedAt = Date()),
        false
    )

    override fun reduce(currentState: TaskEditorState, event: TaskEditorEvent): TaskEditorState = with(currentState){
        return when(event){
            is TaskEditorUserEvent.SetEditingTask -> copy(
                task = event.todoItem,
                isEditorMode = true
            )
            is TaskEditorUserEvent.SetDate -> copy(
                task = task.copy(
                    time = event.date
                )
            )
            is TaskEditorUserEvent.SetImportance -> copy(
                task = task.copy(
                    importance = event.importance
                )
            )
            is TaskEditorUserEvent.UpdateText -> copy(
                task = task.copy(
                    text = event.text
                )
            )
            else -> currentState
        }
    }

    override suspend fun onSideEffect(
        prevState: TaskEditorState,
        newState: TaskEditorState,
        event: TaskEditorEvent
    ) {
        when(event){
            TaskEditorUserEvent.Delete -> {
                taskRepository.deleteTask(newState.task.toDto())
                withContext(Dispatchers.Main){
                    showSideEffect(TaskEditorSideEffect.Close)
                }
            }
            TaskEditorUserEvent.Save -> {
                taskRepository.updateTask(newState.task.toDto())
                withContext(Dispatchers.Main){
                    showSideEffect(TaskEditorSideEffect.Close)
                }
            }
            else -> Unit
        }
    }
}