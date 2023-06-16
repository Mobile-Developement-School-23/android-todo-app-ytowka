package com.danilkha.yandextodo.ui.list

import androidx.lifecycle.viewModelScope
import com.danilkha.yandextodo.domain.TodoItemsRepository
import com.danilkha.yandextodo.domain.models.toModel
import com.danilkha.yandextodo.ui.utils.MviViewModel
import kotlinx.coroutines.launch

class TodoListViewModel constructor(
    val todoListRepository: TodoItemsRepository
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
                todoListRepository.updateCompletedState(event.id, event.isChecked)
            }
            else -> {}
        }
    }

    fun getTasks(){
        viewModelScope.launch {
            val tasks = todoListRepository.getAllTasks().map {
                it.toModel()
            }
            processEvent(TodoListEvent.TaskLoaded(tasks))
        }
    }
}