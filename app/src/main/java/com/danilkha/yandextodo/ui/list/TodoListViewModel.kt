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
                    tasks = currentState.tasks.mapIndexed { index, item ->
                        if(index == event.index){
                            item.copy(completed = event.isChecked)
                        }else item
                    }
                )
            }
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