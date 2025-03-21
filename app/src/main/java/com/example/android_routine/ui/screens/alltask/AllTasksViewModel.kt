package com.example.android_routine.ui.screens.alltask

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.data.source.TasksDao
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class AllTasksViewModel(
    val dao: TasksDao
) : ViewModel() {
    private val _tasks: MutableState<List<TaskVM>> =
        var tasks: State<List<TaskVM>> = _tasks
        var job: Job? = null

    init {
        loadTasks()
    }

    private fun loadTasks() {
        job?.cancel()

        job = dao.getTasks().onEach { tasks ->
            _tasks.value = tasks.map{
                TaskVM.fromEntity(it)
            }
        }.launchIn(viewModelScope)
    }

}