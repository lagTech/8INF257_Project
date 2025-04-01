package com.example.android_routine.data.repository

import com.example.android_routine.data.model.Task
import com.example.android_routine.data.source.TasksDao
import kotlinx.coroutines.flow.Flow


class TaskRepository(private val dao: TasksDao) {

    fun getTasks(): Flow<List<Task>> = dao.getTasks()

    suspend fun getTaskById(id: Int): Task? = dao.getTask(id)

    suspend fun upsert(task: Task) = dao.upsertTask(task)

    suspend fun delete(task: Task) = dao.deleteTask(task)
}
