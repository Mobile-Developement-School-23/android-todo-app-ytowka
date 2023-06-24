package com.danilkha.yandextodo.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.danilkha.yandextodo.data.local.entity.TaskEntity

@Dao
interface TaskDao {


    @Query("SELECT * FROM ${TaskEntity.TABLE_NAME}")
    fun getAllTasks(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(taskEntity: TaskEntity)

    @Delete
    fun deleteTask(taskEntity: TaskEntity)

    @Update
    fun updateTask(taskEntity: TaskEntity)

    @Update(TaskEntity::class)
    fun updateDone(taskDone: TaskEntity.Done)
}