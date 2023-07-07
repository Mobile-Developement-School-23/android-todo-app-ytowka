package com.danilkha.yandextodo.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danilkha.yandextodo.App
import com.danilkha.yandextodo.data.local.database.TaskDao
import com.danilkha.yandextodo.data.local.database.TaskDatabase
import dagger.Module
import dagger.Provides

@Module
object DBModule {

    @Provides
    @AppScope
    fun provideDatabase(app: App): TaskDatabase{
        return Room.databaseBuilder(app, TaskDatabase::class.java, name = "task_database")
            .build()
    }


    @Provides
    fun getTaskDao(database: TaskDatabase): TaskDao{
        return database.taskDao()
    }

    @Provides
    fun getSharedPrefs(app: App): SharedPreferences{
        return app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
}