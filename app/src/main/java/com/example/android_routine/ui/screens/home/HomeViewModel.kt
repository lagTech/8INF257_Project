package com.example.android_routine.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.data.viewmodelobject.TaskVM
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    data class HomeUiState (
        val tasks: List<Task> = emptyList(),
        val filteredTasks: List<Task> = emptyList(),
        val searchQuery: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
        val todayTasks: List<Task> = emptyList()

    )
    sealed class HomeEvent {
        data class ToggleComplete(val taskId: Int) : HomeEvent()
        data class DeleteTask(val taskId: Int) : HomeEvent()
        data class UpdateSearch(val query: String) : HomeEvent()
        object Load : HomeEvent()
    }
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object NavigateToAllTasks : UiEvent()
    }

    private var fullTaskList: List<TaskVM> = emptyList()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val allTasks: StateFlow<List<Task>> = repository.getTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

     init {
         onEvent(HomeEvent.Load)
     }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ToggleComplete -> toggleTaskCompletion(event.taskId)
            is HomeEvent.DeleteTask -> deleteTask(event.taskId)
            is HomeEvent.UpdateSearch -> updateSearchQuery(event.query)
            is HomeEvent.Load -> observeTasks()
        }
    }

    private fun observeTasks() {
        viewModelScope.launch {
            allTasks.collect { tasks ->
                _uiState.update { current ->
                    val query = current.searchQuery
                    val filtered = filterTasks(tasks, query)
                    val allTodayTasks = tasks.filter { isToday(it) }
                    val filteredTodayTasks = filterTasks(allTodayTasks, query)
                    current.copy(tasks = tasks, filteredTasks = filtered, todayTasks = filteredTodayTasks)

                }
            }
        }
    }

    private fun updateSearchQuery(query: String) {
        _uiState.update { current ->
            val filtered = filterTasks(current.tasks, query)
            val filteredTodayTasks = filterTasks(current.tasks.filter { isToday(it) }, query)

            current.copy(searchQuery = query, filteredTasks = filtered, todayTasks = filteredTodayTasks)
        }
    }

    private fun filterTasks(tasks: List<Task>, query: String): List<Task> {
        return if (query.isBlank()) {
            tasks
        } else {
            tasks.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.categoryId?.toString()?.contains(query) == true
            }
        }
    }

    private fun toggleTaskCompletion(taskId: Int) {
        viewModelScope.launch {
            val task = repository.getTaskById(taskId)
            if (task != null) {
                val updated = task.copy(isCompleted = !task.isCompleted)
                repository.upsert(updated)
                _eventFlow.emit(UiEvent.ShowSnackbar("Task updated"))
            }
        }
    }

    private fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            repository.getTaskById(taskId)?.let {
                repository.delete(it)
                _eventFlow.emit(UiEvent.ShowSnackbar("Task deleted"))
            }
        }
    }

    private fun isToday(task: Task): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dueDate = LocalDate.parse(task.dueDate ?: "", formatter)
            dueDate == LocalDate.now()
        } catch (e: Exception) {
            false
        }
    }



}

