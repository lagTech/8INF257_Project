package com.example.android_routine.ui.screens.allcategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.model.Category
import com.example.android_routine.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: CategoryRepository
):ViewModel() {


    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    sealed class CategoryEvent {
        data class DeleteCategory(val category: Category) : CategoryEvent()
        data class UpdateCategory(val category: Category) : CategoryEvent()
        data class UpdateQuery(val query: String) : CategoryEvent()
        data class UpdateNewCategoryName(val name: String) : CategoryEvent()
        object AddCategory : CategoryEvent()
        object LoadCategories : CategoryEvent()
    }

    init {
        onEvent(CategoryEvent.LoadCategories)
    }

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.UpdateQuery -> {
                _uiState.update { it.copy(query = event.query) }
                filterCategories()
            }

            is CategoryEvent.UpdateNewCategoryName -> {
                _uiState.update { it.copy(newCategoryName = event.name) }
            }

            is CategoryEvent.AddCategory -> {
                addCategory()
            }

            is CategoryEvent.LoadCategories -> {
                loadCategories()
            }
            is CategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    repository.delete(event.category)
                }
            }
            is CategoryEvent.UpdateCategory -> {
                viewModelScope.launch {
                    repository.upsert(event.category)
                }
            }
        }
    }
    private fun loadCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { all ->
                _uiState.update { it.copy(allCategories = all) }
                filterCategories()
            }
        }
    }

    private fun addCategory() {
        val name = _uiState.value.newCategoryName.trim()
        if (name.isNotBlank()) {
            viewModelScope.launch {
                repository.upsert(Category(name = name))
                _uiState.update { it.copy(newCategoryName = "") }
            }
        }
    }


    private fun filterCategories() {
        val query = _uiState.value.query.lowercase()
        val filtered = _uiState.value.allCategories.filter {
            it.name.lowercase().contains(query)
        }
        _uiState.update { it.copy(filteredCategories = filtered) }
    }



}