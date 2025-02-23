package com.example.android_routine.ui.screens.allcategories

import androidx.lifecycle.ViewModel
import com.example.android_routine.data.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CategoryViewModel : ViewModel() {
    data class CategoryUiState(
        val categories: List<Category> = emptyList(),
        val filteredCategories: List<Category> = emptyList(),
        val searchQuery: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    // Mock data
    private val _categories = mutableListOf(
        Category(1, "Personal"),
        Category(2, "Work"),
        Category(3, "Health"),
        Category(4, "Shopping"),
        Category(5, "House")
    )

    init {
        updateCategories()
    }

    fun addCategory(name: String) {
        val newCategory = Category(
            id = _categories.maxOfOrNull { it.id }?.plus(1) ?: 1,
            name = name
        )
        _categories.add(newCategory)
        updateCategories()
    }

    fun deleteCategory(categoryId: Int) {
        _categories.removeAll { it.id == categoryId }
        updateCategories()
    }

    fun searchCategories(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query,
                filteredCategories = if (query.isEmpty()) {
                    _categories
                } else {
                    _categories.filter { it.name.contains(query, ignoreCase = true) }
                }
            )
        }
    }

    private fun updateCategories() {
        _uiState.update { currentState ->
            currentState.copy(
                categories = _categories.toList(),
                filteredCategories = if (currentState.searchQuery.isEmpty()) {
                    _categories.toList()
                } else {
                    _categories.filter {
                        it.name.contains(currentState.searchQuery, ignoreCase = true)
                    }
                }
            )
        }
    }
}