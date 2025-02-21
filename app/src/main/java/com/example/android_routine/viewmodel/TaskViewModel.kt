package com.example.android_routine.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.android_routine.model.Task

open class TaskViewModel : ViewModel() {
    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    fun addTask(task: Task) {
        _tasks.add(task)
    }

    open fun getTodayTasks(): List<Task> {
        return _tasks.filter { it.isToday }
    }
}
