package com.example.android_routine.ui.screens.alltask

import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android_routine.ui.screens.components.BottomNav2
import com.example.android_routine.ui.screens.components.TaskItem2
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun AllTasksScreen(
    navController: NavController,
    allTasksViewModel: AllTasksViewModel
) {
    val uiState by allTasksViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val visibleTasks = remember { mutableStateMapOf<Int, Boolean>() }

    LaunchedEffect(true) {
        allTasksViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                is UiEvent.NavigateToAddTask -> navController.navigate(Screen.AddTask.route)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(bottom = 80.dp)
            ) {
                // Back button
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable { navController.popBackStack() }
                )

                Text(
                    "All tasks",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2196F3),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Overdue Section
                if (uiState.overdueTasks.isNotEmpty()) {
                    Text(
                        "Overdue",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF5722),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    LazyColumn(modifier = Modifier.heightIn(max = 250.dp)) {
                        items(uiState.overdueTasks.take(3), key = { it.id }) { task ->
                            TaskItem2(
                                task = task,
                                onTaskClick = { allTasksViewModel.onEvent(TaskEvent.ToggleComplete(task.id)) },
                                onDelete = { allTasksViewModel.onEvent(TaskEvent.Delete(task.id)) },
                                onItemClick = { navController.navigate(Screen.TaskDetail.createRoute(task.id)) }
                            )
                        }
                    }
                }

                // All Tasks Section
                Text(
                    "All tasks",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2196F3),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                val nonOverdueTasks = uiState.allTasks.filter { task ->
                    task.id !in uiState.overdueTasks.mapNotNull { it.id }
                }

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(nonOverdueTasks, key = { it.id }) { task ->
                        val taskId = task.id
                        val isVisible = visibleTasks.getOrPut(taskId) { true }

                        if (isVisible) {
                            val dismissState = rememberDismissState(
                                confirmStateChange = { dismissValue ->
                                    if (dismissValue == DismissValue.DismissedToStart) {
                                        visibleTasks[taskId] = false
                                        coroutineScope.launch {
                                            delay(300)
                                            allTasksViewModel.onEvent(TaskEvent.Delete(taskId))
                                        }
                                        true
                                    } else false
                                }
                            )

                            AnimatedVisibility(
                                visible = isVisible,
                                exit = slideOutHorizontally(tween(300)) { it } + fadeOut()
                            ) {
                                SwipeToDismiss(
                                    state = dismissState,
                                    directions = setOf(DismissDirection.EndToStart),
                                    background = {
                                        val bgColor by animateColorAsState(
                                            if (dismissState.targetValue == DismissValue.Default)
                                                Color.Transparent else Color.Red
                                        )

                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(bgColor)
                                                .padding(end = 24.dp),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            if (dismissState.targetValue != DismissValue.Default) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
                                                    Text(
                                                        text = "Delete",
                                                        color = Color.White,
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(start = 8.dp)
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    dismissContent = {
                                        TaskItem2(
                                            task = task,
                                            onTaskClick = {
                                                allTasksViewModel.onEvent(TaskEvent.ToggleComplete(task.id))
                                            },
                                            onDelete = {
                                                allTasksViewModel.onEvent(TaskEvent.Delete(task.id))
                                            },
                                            onItemClick = {
                                                navController.navigate(Screen.TaskDetail.createRoute(task.id))
                                            }
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // FAB
        FloatingActionButton(
            onClick = { navController.navigate(Screen.AddTask.route) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp),
            containerColor = Color(0xFF2196F3),
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation()
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }

        // Bottom Navigation
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNav2()
        }
    }
}
