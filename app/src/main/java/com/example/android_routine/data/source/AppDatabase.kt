package com.example.android_routine.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_routine.data.model.Category
import com.example.android_routine.data.model.Task

@Database(entities = [Task::class, Category::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val taskDao: TasksDao
    abstract val categoryDao: CategoryDao

    companion object{
        const val DATABASE_NAME = "app_routine.db"
    }
}