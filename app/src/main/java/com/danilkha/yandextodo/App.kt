package com.danilkha.yandextodo

import android.app.Application
import com.danilkha.yandextodo.domain.TodoItemsRepository

class App : Application(){

    val repository by lazy { TodoItemsRepository.Impl() }


}