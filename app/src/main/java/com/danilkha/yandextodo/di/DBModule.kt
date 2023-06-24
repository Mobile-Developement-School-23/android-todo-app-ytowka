package com.danilkha.yandextodo.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danilkha.yandextodo.App
import com.danilkha.yandextodo.data.local.database.TaskDatabase

class DBModule(val app: App) {

    val database: TaskDatabase by lazy {
        Room.databaseBuilder(app, TaskDatabase::class.java, name = "task_database")
            .build()
    }

    val taskDao
        get() = database.taskDao()

    val sharedPrefs: SharedPreferences
        get() = app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
}