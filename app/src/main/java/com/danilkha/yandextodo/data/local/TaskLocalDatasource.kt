package com.danilkha.yandextodo.data.local

import android.content.SharedPreferences
import com.danilkha.yandextodo.data.local.database.TaskDao

interface TaskLocalDatasource {

    class Impl(
        private val taskDao: TaskDao,
        private val sharedPreferences: SharedPreferences,
    ) : TaskLocalDatasource{

    }
}