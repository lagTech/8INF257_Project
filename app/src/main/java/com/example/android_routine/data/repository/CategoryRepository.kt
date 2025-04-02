package com.example.android_routine.data.repository

import com.example.android_routine.data.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository{
    fun getCategories(): Flow<List<Category>>
    suspend fun getCategoryById(id: Int): Category?
    suspend fun upsert(category: Category)
    suspend fun delete(category: Category)
}
