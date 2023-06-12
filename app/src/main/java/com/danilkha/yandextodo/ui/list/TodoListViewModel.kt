package com.danilkha.yandextodo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilkha.yandextodo.domain.TodoItemsRepository
import com.danilkha.yandextodo.models.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoListViewModel constructor(
    val todoListRepository: TodoItemsRepository
) : ViewModel() {

    private val _taskList = MutableLiveData<List<TodoItem>>()
    val taskList: LiveData<List<TodoItem>> get() = _taskList

    init {
        getTasks()
    }

    fun getTasks(){
        viewModelScope.launch {
            _taskList.value = todoListRepository.getAllTasks()
        }
    }
}