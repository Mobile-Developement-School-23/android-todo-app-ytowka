package com.danilkha.yandextodo.ui.editor

import com.danilkha.yandextodo.domain.models.toModel
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.usecase.task.CreateTaskUseCase
import com.danilkha.yandextodo.domain.usecase.task.DeleteTaskUseCase
import com.danilkha.yandextodo.domain.usecase.task.GetTaskUseCase
import com.danilkha.yandextodo.domain.usecase.task.UpdateTaskUseCase
import com.danilkha.yandextodo.ui.list.TodoListSideEffect
import com.danilkha.yandextodo.ui.models.Importance
import com.danilkha.yandextodo.ui.models.TodoItem
import com.danilkha.yandextodo.ui.models.toDto
import com.danilkha.yandextodo.ui.utils.MviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class TaskEditorViewModel @Inject constructor(
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
): MviViewModel<TaskEditorState, TaskEditorEvent, TaskEditorUserEvent, TaskEditorSideEffect>(){

    override val startState: TaskEditorState = TaskEditorState(
        TodoItem("", "", Importance.NORMAL, time = null, completed = false, createdAt = Date(), updatedAt = Date()),
        false
    )

    override fun reduce(currentState: TaskEditorState, event: TaskEditorEvent): TaskEditorState = with(currentState){
        return when(event){
            is TaskEditorUserEvent.SetEditingTaskId -> copy(
                isEditorMode = true
            )
            is TaskEditorEvent.TaskLoaded -> copy(
                task = event.todoItem
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
            is TaskEditorUserEvent.SetEditingTaskId -> {
                val task = getTaskUseCase.execute(event.id)
                processEvent(TaskEditorEvent.TaskLoaded(task.toModel()))
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