package com.example.android_routine.data.repository

import com.example.android_routine.data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.*

class TaskRepositoryImpl : TaskRepository {

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date()) // Returns today's date
    }

    // Mock data - in real app this would come from database/API
    private val _tasks = MutableStateFlow<List<Task>>(
        mutableListOf(
        Task(
            id = 1,
            title = "Morning Meeting",
            category = "Work",
            dueTime = "09:00",
            dueDate = getCurrentDate(),
            notes = "Daily standup",
            priority = "high",
            periodicity = "everyday",

        ),
        Task(
            id = 2,
            title = "Gym Session",
            category = "Health",
            dueTime = "06:00",
            dueDate = getCurrentDate(),
            priority = "high",
            periodicity = "everyday",
            notes = "Cardio day",
        ),
        Task(
            id = 3,
            title = "push my changes",
            category = "Personal",
            dueTime = "04:00",
            dueDate = getCurrentDate(),
            notes = "Buy groceries",
            priority = "high",
            periodicity = "everyday",
        ),

        Task(
            id = 4,
            title = "Shopping",
            category = "Personal",
            dueTime = "09:00",
            dueDate = getCurrentDate(),
            notes = "Buy groceries",
            priority = "high",
            periodicity = "everyday",
        ),

    ))


    override fun getAllTasks(): StateFlow<List<Task>> = _tasks  // Return StateFlow

    override fun getTask(id: Int): Task? = _tasks.value.find { it.id == id }

    override fun addTask(task: Task) {
        _tasks.update { currentTasks ->
            currentTasks + task
        }
    }

    override fun updateTask(task: Task) {
        _tasks.update { currentTasks ->
            currentTasks.map { if (it.id == task.id) task else it }
        }
    }

    override fun deleteTask(taskId: Int) {
        _tasks.update { currentTasks ->
            currentTasks.filterNot { it.id == taskId }
        }
    }

    override fun toggleTaskCompletion(taskId: Int) {
        _tasks.update { currentTasks ->
            currentTasks.map { task ->
                if (task.id == taskId) task.copy(isCompleted = !task.isCompleted) else task
            }
        }
    }


}