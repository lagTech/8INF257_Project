package com.example.android_routine.data.repository

import com.example.android_routine.data.model.Task
import kotlinx.coroutines.flow.Flow


interface TaskRepository {

     fun getTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: Int): Task?
    suspend fun upsert(task: Task)
    suspend fun delete(task: Task)
}
