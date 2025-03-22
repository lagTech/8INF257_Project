package com.example.android_routine.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    data class HomeUiState (
        val tasks: List<Task> = emptyList(),
        val filteredTasks: List<Task> = emptyList(),
        val searchQuery: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

     private val allTasks: StateFlow<List<Task>> = repository.getAllTasks()
         .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

     private val _uiState = MutableStateFlow(HomeUiState())
     val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

     init {
         observeTasks()
     }

     /** Collects tasks from repository and updates UI state automatically */
     private fun observeTasks() {
         viewModelScope.launch {
             allTasks.collect { tasks ->
                 _uiState.update { currentState ->
                     val filteredTasks = if (currentState.searchQuery.isEmpty()) tasks else {
                         tasks.filter { it.title.contains(currentState.searchQuery, ignoreCase = true) ||
                                 it.category.contains(currentState.searchQuery, ignoreCase = true) }
                     }
                     currentState.copy(tasks = tasks, filteredTasks = filteredTasks)
                 }
             }
         }
     }

     /** Search for tasks */
     fun searchTasks(query: String) {
         _uiState.update { currentState ->
             val filteredTasks = if (query.isEmpty()) allTasks.value else {
                 allTasks.value.filter { it.title.contains(query, ignoreCase = true) ||
                         it.category.contains(query, ignoreCase = true) }
             }
             currentState.copy(searchQuery = query, filteredTasks = filteredTasks)
         }
     }










}

