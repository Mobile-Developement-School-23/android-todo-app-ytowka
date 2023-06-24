package com.danilkha.yandextodo.ui.list

import androidx.lifecycle.viewModelScope
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import com.danilkha.yandextodo.domain.models.toModel
import com.danilkha.yandextodo.domain.usecase.task.GetAllTasksUseCase
import com.danilkha.yandextodo.domain.usecase.task.UpdateTaskCompeteUseCase
import com.danilkha.yandextodo.ui.utils.MviViewModel
import kotlinx.coroutines.launch

class TodoListViewModel constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val updateTaskCompeteUseCase: UpdateTaskCompeteUseCase,
) : MviViewModel<TodoListState, TodoListEvent, TodoListUserEvent, TodoListSideEffect>() {

    override val startState: TodoListState
        get() = TodoListState(listOf())

    init {
        getTasks()
    }

    override fun reduce(currentState: TodoListState, event: TodoListEvent): TodoListState {
        return when(event){
            is TodoListEvent.TaskLoaded -> currentState.copy(
                tasks = event.tasks
            )

            is TodoListUserEvent.UpdateCheckedState -> {
                currentState.copy(
                    tasks = currentState.tasks.map { item ->
                        if(item.id == event.id){
                            item.copy(completed = event.isChecked)
                        }else item
                    }
                )
            }

            is TodoListUserEvent.ToggleCompletedTasks -> {
                currentState.copy(showCompleted = !currentState.showCompleted)
            }
            else -> currentState
        }
    }

    override suspend fun onSideEffect(
        prevState: TodoListState,
        newState: TodoListState,
        event: TodoListEvent
    ) {
        when(event){
            TodoListUserEvent.UpdateData -> getTasks()
            is TodoListUserEvent.UpdateCheckedState -> {
                updateTaskCompeteUseCase(UpdateTaskCompeteUseCase.Params(event.id, event.isChecked))
            }
            else -> {}
        }
    }

    fun getTasks(){
        viewModelScope.launch {
            getAllTasksUseCase().onSuccess { tasks ->
                processEvent(TodoListEvent.TaskLoaded(tasks.map {
                    it.toModel()
                }))
            }
        }
    }
}