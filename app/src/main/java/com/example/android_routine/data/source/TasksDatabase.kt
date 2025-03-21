package com.example.android_routine.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_routine.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {

    abstract val dao: TasksDao

    companion object{
        const val DATABASE_NAME = "tasks.db"
    }
}