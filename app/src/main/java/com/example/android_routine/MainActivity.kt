package com.example.android_routine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.android_routine.data.source.AppDatabase
import com.example.android_routine.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}



