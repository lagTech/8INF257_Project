package com.example.android_routine.data.repository

import com.example.android_routine.data.model.Category
import com.example.android_routine.data.source.CategoryDao
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val dao: CategoryDao) : CategoryRepository {
    override fun getCategories(): Flow<List<Category>> = dao.getCategories()
    override suspend fun getCategoryById(id: Int): Category? = dao.getCategoryById(id)
    override suspend fun upsert(category: Category) = dao.insertCategory(category)
    override suspend fun delete(category: Category) = dao.deleteCategory(category)
}
