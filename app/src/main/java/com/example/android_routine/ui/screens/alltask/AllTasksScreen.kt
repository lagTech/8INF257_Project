package com.example.android_routine.ui.screens.alltask


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.example.android_routine.data.repository.CategoryRepositoryImpl
import com.example.android_routine.data.repository.TaskRepositoryImpl
import com.example.android_routine.data.source.AppDatabase
import com.example.android_routine.ui.screens.components.BottomNav2
import com.example.android_routine.ui.screens.components.TaskItem2
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AllTasksScreen(
    navController: NavController,
    allTasksViewModel: AllTasksViewModel
) {
    val context = LocalContext.current

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).build()

    val taskRepo = TaskRepositoryImpl(db.taskDao)
    val categoryRepo = CategoryRepositoryImpl(db.categoryDao)

    val factory = AllTasksViewModelFactory(taskRepo, categoryRepo)
    val viewModel: AllTasksViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                is UiEvent.NavigateToAddTask -> navController.navigate("add_task")
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading){
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center))
        }else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(bottom = 80.dp)
            ) {
                Text(
                    "All tasks",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2196F3),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Overdue section
                if (uiState.overdueTasks.isNotEmpty()) {
                    Text(
                        "Overdue",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF5722),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .weight(0.3f)
                            .fillMaxWidth()
                    ) {
                        LazyColumn {
                            items(
                                items = uiState.overdueTasks,
                                key = { it.id }
                            ) { task ->
                                TaskItem2(
                                    task = task,
                                    onTaskClick = { viewModel.onEvent(TaskEvent.ToggleComplete(task.id)) },
                                    onDelete = { viewModel.onEvent(TaskEvent.Delete(task.id)) }
                                )
                            }
                        }
                    }
                }

                // All tasks section
                Text(
                    "All tasks",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2196F3),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(
                            items = uiState.allTasks,
                            key = { it.id }
                        ) { task ->
                            TaskItem2(
                                task = task,
                                onTaskClick = { viewModel.onEvent(TaskEvent.ToggleComplete(task.id)) },
                                onDelete = { viewModel.onEvent(TaskEvent.Delete(task.id)) }
                            )
                        }
                    }
                }
            }
        }


        // FAB
        FloatingActionButton(
            onClick = { /* Add new task */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp),
            containerColor = Color(0xFF2196F3),
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }

        // Bottom Navigation
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomNav2()
        }
    }

}

