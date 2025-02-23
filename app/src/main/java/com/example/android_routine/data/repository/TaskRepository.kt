package com.example.android_routine.data.repository

import com.example.android_routine.data.model.Task

interface TaskRepository {
    fun getAllTasks(): List<Task>
    fun getTask(id: Int): Task?
    fun updateTask(task: Task)
    fun deleteTask(taskId: Int)
    fun addTask(task: Task)
    fun toggleTaskCompletion(taskId: Int)
}