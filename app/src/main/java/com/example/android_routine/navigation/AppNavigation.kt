package com.example.android_routine.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_routine.view.screens.AddTaskScreen
import com.example.android_routine.view.screens.AllTasksScreen
import com.example.android_routine.view.screens.HomeScreen
import com.example.android_routine.viewmodel.TaskViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: TaskViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("addTask") { AddTaskScreen(navController, viewModel) }
        composable("allTasks") { AllTasksScreen(navController, viewModel) }
    }
}
