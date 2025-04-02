package com.example.android_routine.ui.screens.allcategories

import com.example.android_routine.data.model.Category

data class CategoryUiState(
    val allCategories: List<Category> = emptyList(),
    val filteredCategories: List<Category> = emptyList(),
    val query: String = "",
    val newCategoryName: String = ""
)