package com.example.android_routine.data.repository

import com.example.android_routine.data.model.Task

class TaskRepositoryImpl : TaskRepository {
    // Mock data - in real app this would come from database/API
    private val _tasks = mutableListOf(
        Task(
            id = 1,
            title = "Morning Meeting",
            category = "Work",
            dueTime = "09:00 AM",
            dueDate = "",
            notes = "Daily standup",
            isToday = true
        ),
        Task(
            id = 2,
            title = "Gym Session",
            category = "Health",
            dueTime = "06:00 PM",
            dueDate = "",
            notes = "Cardio day",
            isToday = true
        ),
        Task(
            id = 3,
            title = "Shopping",
            category = "Personal",
            dueTime = "04:00 PM",
            dueDate = "",
            notes = "Buy groceries",
            isToday = false
        ),

        Task(
            id = 4,
            title = "Shopping",
            category = "Personal",
            dueTime = "04:00 PM",
            dueDate = "",
            notes = "Buy groceries",
            isToday = false
        ),
        Task(
            id = 5,
            title = "drink water",
            category = "Personal",
            dueTime = "04:00 PM",
            dueDate = "",
            notes = "Buy groceries",
            isToday = true
        ),

        Task(
            id = 6,
            title = "Shopping",
            category = "Personal",
            dueTime = "04:00 PM",
            dueDate = "",
            notes = "Buy groceries",
            isToday = true
        )


    )

    override fun getAllTasks(): List<Task> = _tasks.toList()

    override fun getTask(id: Int): Task? = _tasks.find { it.id == id }

    override fun updateTask(task: Task) {
        val index = _tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            _tasks[index] = task
        }
    }

    override fun deleteTask(taskId: Int) {
        _tasks.removeAll { it.id == taskId }
    }

    override fun addTask(task: Task) {
        _tasks.add(task)
    }

    override fun toggleTaskCompletion(taskId: Int) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            _tasks[index] = _tasks[index].copy(isCompleted = !_tasks[index].isCompleted)
        }
    }
}