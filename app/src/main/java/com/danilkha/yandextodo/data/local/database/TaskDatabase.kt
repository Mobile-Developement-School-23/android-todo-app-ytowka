package com.danilkha.yandextodo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danilkha.yandextodo.data.local.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}