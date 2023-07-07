package com.danilkha.yandextodo.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.danilkha.yandextodo.data.local.entity.TaskEntity

@Dao
interface TaskDao {


    @Query("SELECT * FROM ${TaskEntity.TABLE_NAME}")
    fun getAllTasks(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(taskEntity: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTasks(taskEntities: List<TaskEntity>)

    @Query("SELECT * FROM ${TaskEntity.TABLE_NAME} WHERE ${TaskEntity.TASK_ID} = :id")
    fun getTaskById(id: String): TaskEntity

    @Query("DELETE FROM ${TaskEntity.TABLE_NAME} WHERE ${TaskEntity.TASK_ID} = :id")
    fun deleteTask(id: String)

    @Update
    fun updateTask(taskEntity: TaskEntity)

    @Update(TaskEntity::class)
    fun updateDone(taskDone: TaskEntity.Done)

    @Query("DELETE FROM ${TaskEntity.TABLE_NAME}")
    fun clearTable()

    @Transaction
    fun updateCache(taskEntities: List<TaskEntity>){
        clearTable()
        insertTasks(taskEntities)
    }
}