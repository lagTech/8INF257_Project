package com.example.android_routine.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_routine.ui.screens.allcategories.AllCategoriesScreen
import com.example.android_routine.ui.screens.alltask.AllTasksScreen
import com.example.android_routine.ui.screens.home.HomeScreen
import com.example.android_routine.ui.screens.alltask.AllTasksViewModel
import com.example.android_routine.ui.screens.home.HomeViewModel
import androidx.navigation.navArgument
import com.example.android_routine.data.repository.CategoryRepositoryImpl
import com.example.android_routine.data.repository.TaskRepositoryImpl
import com.example.android_routine.data.source.AppDatabase
import com.example.android_routine.ui.screens.addtask.AddTaskViewModel
import com.example.android_routine.ui.screens.addtask.AddTaskViewModelFactory
import com.example.android_routine.ui.screens.taskdetail.TaskDetailScreen
import com.example.android_routine.ui.screens.taskdetail.TaskDetailViewModelFactory
import com.example.android_routine.ui.screens.addtask.AddTaskScreen
import com.example.android_routine.ui.screens.allcategories.CategoryViewModel
import com.example.android_routine.ui.screens.detail.TaskDetailViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Application context for DB
    val context = LocalContext.current.applicationContext
    val database = remember { AppDatabase.getInstance(context) }

    // Repositories with DAO injected
    val taskRepository = remember { TaskRepositoryImpl(database.taskDao) }
    val categoryRepository = remember { CategoryRepositoryImpl(database.categoryDao) }


    val homeViewModel: HomeViewModel = viewModel {
        HomeViewModel(taskRepository)
    }
    val allTasksViewModel: AllTasksViewModel = viewModel {
        AllTasksViewModel(taskRepository, categoryRepository)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route)
        { HomeScreen(navController = navController, viewModel = homeViewModel) }

        composable(Screen.AddTask.route) {
            val addTaskViewModel: AddTaskViewModel = viewModel(
                factory = AddTaskViewModelFactory(taskRepository)
            )
            AddTaskScreen(navController, addTaskViewModel)
        }
        composable(Screen.AllTasks.route)
        { AllTasksScreen(navController, allTasksViewModel) }

        composable(Screen.AllCategories.route) {
            val categoryViewModel: CategoryViewModel = viewModel {
                CategoryViewModel(categoryRepository)
            }
            AllCategoriesScreen(navController, categoryViewModel)
        }

        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            val viewModel: TaskDetailViewModel = viewModel(
                factory = TaskDetailViewModelFactory(taskId, taskRepository, categoryRepository)
            )
            TaskDetailScreen(navController, viewModel)
        }
    }

}


