package com.danilkha.yandextodo.di

import com.danilkha.yandextodo.ui.editor.TaskEditorViewModel
import com.danilkha.yandextodo.ui.list.TodoListViewModel
import dagger.Subcomponent

@Subcomponent
abstract class ViewModelComponent {

    abstract val todoListViewModel: TodoListViewModel
    abstract val taskEditorViewModel: TaskEditorViewModel
}