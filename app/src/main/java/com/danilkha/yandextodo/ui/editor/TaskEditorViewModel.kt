package com.danilkha.yandextodo.ui.editor

import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.usecase.task.CreateTaskUseCase
import com.danilkha.yandextodo.domain.usecase.task.DeleteTaskUseCase
import com.danilkha.yandextodo.domain.usecase.task.UpdateTaskUseCase
import com.danilkha.yandextodo.ui.list.TodoListSideEffect
import com.danilkha.yandextodo.ui.models.Importance
import com.danilkha.yandextodo.ui.models.TodoItem
import com.danilkha.yandextodo.ui.models.toDto
import com.danilkha.yandextodo.ui.utils.MviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class TaskEditorViewModel constructor(
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
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
                deleteTaskUseCase(newState.task.toDto()).onSuccess {
                    withContext(Dispatchers.Main){
                        showSideEffect(TaskEditorSideEffect.DataUpdated)
                    }
                }.onFailure {
                    showSideEffect(TaskEditorSideEffect.Error(it))
                }
            }
            TaskEditorUserEvent.Save -> {
                if(newState.isValid){
                    val result = if (newState.isEditorMode){
                        updateTaskUseCase(newState.task.toDto())
                    }else{
                        createTaskUseCase(newState.task.toDto())
                    }
                    result.onSuccess {
                        withContext(Dispatchers.Main){
                            showSideEffect(TaskEditorSideEffect.DataUpdated)
                        }
                    }.onFailure {
                        showSideEffect(TaskEditorSideEffect.Error(it))
                    }
                }
            }
            else -> Unit
        }
    }
}