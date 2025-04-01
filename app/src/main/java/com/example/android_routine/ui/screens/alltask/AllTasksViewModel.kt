package com.example.android_routine.ui.screens.alltask

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.source.TasksDao
import com.example.android_routine.data.viewmodelobject.TaskVM
import kotlinx.coroutines.Job

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