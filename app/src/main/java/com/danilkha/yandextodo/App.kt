package com.danilkha.yandextodo

import android.app.Application
import com.danilkha.yandextodo.di.MainModule
import com.danilkha.yandextodo.domain.repository.TodoItemsRepository

class App : Application(){

    val mainModule by lazy { MainModule(this) }


}