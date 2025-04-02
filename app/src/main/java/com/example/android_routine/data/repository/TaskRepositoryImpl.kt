package com.example.android_routine.data.repository

import com.example.android_routine.data.model.Task
import com.example.android_routine.data.source.TasksDao
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val dao: TasksDao) : TaskRepository{
    override fun getTasks(): Flow<List<Task>> = dao.getTasks()
    override suspend fun getTaskById(id: Int): Task? = dao.getTaskById(id)
    override suspend fun upsert(task: Task) = dao.upsertTask(task)
    override suspend fun delete(task: Task) = dao.deleteTask(task)
}