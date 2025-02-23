package com.example.android_routine.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class HomeViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    data class HomeUiState (
        val tasks: List<Task> = emptyList(),
        val filteredTasks: List<Task> = emptyList(),
        val searchQuery: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState= MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        //Initialize the Ui state with all tasks
        updateTasks()
    }



//    fun getTasksByCategory(category: String): List<Task> {
//        return _tasks.filter { it.category == category }
//    }

    fun searchTasks(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query,
                filteredTasks = if (query.isEmpty()) {
                    repository.getAllTasks()
                } else {
                    repository.getAllTasks().filter {
                        it.title.contains(query, ignoreCase = true) ||
                                it.category.contains(query, ignoreCase = true)
                    }
                }
            )
        }
    }

    fun deleteTask(taskId: Int) {
        repository.deleteTask(taskId)
        updateTasks()
    }

    fun toggleTaskCompletion(taskId: Int) {
        repository.toggleTaskCompletion(taskId)
        updateTasks()
    }

    private fun updateTasks() {
        _uiState.update { currentState ->
            currentState.copy(
                tasks = repository.getAllTasks(),
                filteredTasks = if (currentState.searchQuery.isEmpty()) {
                    repository.getAllTasks()
                } else {
                    repository.getAllTasks().filter {
                        it.title.contains(currentState.searchQuery, ignoreCase = true) ||
                                it.category.contains(currentState.searchQuery, ignoreCase = true)
                    }
                }
            )
        }
    }



    // Error handling
    private fun handleError(error: Exception) {
        _uiState.update { it.copy(error = error.message) }
    }


}

