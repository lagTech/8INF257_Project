package com.example.android_routine.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.android_routine.data.repository.TaskRepositoryImpl
import com.example.android_routine.ui.screens.addtask.AddTaskViewModel
import com.example.android_routine.ui.screens.addtask.AddTaskViewModelFactory
import com.example.android_routine.ui.screens.taskdetail.TaskDetailScreen
import com.example.android_routine.ui.screens.taskdetail.TaskDetailViewModel
import com.example.android_routine.ui.screens.taskdetail.TaskDetailViewModelFactory
import com.example.android_routine.ui.screens.addtask.AddTaskScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val repository = remember { TaskRepositoryImpl() }

    val homeViewModel: HomeViewModel = viewModel { HomeViewModel(repository) }
    val allTasksViewModel: AllTasksViewModel = viewModel { AllTasksViewModel(repository) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController, homeViewModel) }
        composable(Screen.AddTask.route) {
            val addTaskViewModel: AddTaskViewModel = viewModel(
                factory = AddTaskViewModelFactory(repository)
            )
            AddTaskScreen(navController, addTaskViewModel)
        }
        composable(Screen.AllTasks.route) { AllTasksScreen(navController, allTasksViewModel) }
        composable(Screen.AllCategories.route) { AllCategoriesScreen() }
        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            val factory = remember { TaskDetailViewModelFactory(repository) }
            val viewModel: TaskDetailViewModel = viewModel(factory = factory)

            TaskDetailScreen(
                taskId = taskId,
                navController = navController,
                viewModel = viewModel
            )
        }

    }
}
