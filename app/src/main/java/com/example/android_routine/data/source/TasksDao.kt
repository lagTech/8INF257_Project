package com.example.android_routine.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.android_routine.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * FROM task")
    fun getTasks() : Flow<List<Task>>

    @Query("SELECT * FROM task WHERE ID = :id")
    suspend fun getTaskById(id: Int): Task?

    @Upsert
    suspend fun upsertTask(task: Task): Long

    @Delete
    suspend fun deleteTask(task: Task)
}