package com.example.android_routine.ui.screens.taskdetail

import androidx.compose.runtime.getValue
import androidx.compose.material.icons.outlined.Done
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android_routine.data.model.Category
import com.example.android_routine.ui.screens.allcategories.CategoryViewModel
import com.example.android_routine.ui.screens.components.BottomNav
import com.example.android_routine.ui.screens.components.BottomNav2
import com.example.android_routine.ui.screens.components.CategoryDropDownMenu
import com.example.android_routine.ui.screens.components.PeriodicityDropDown
import com.example.android_routine.ui.screens.components.PrioritySection
import com.example.android_routine.ui.screens.detail.TaskDetailViewModel


@Composable
fun TaskDetailScreen(
    navController: NavController,
    viewModel: TaskDetailViewModel,
    categoryViewModel: CategoryViewModel
) {
    val categoryUiState by categoryViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is TaskDetailViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                is TaskDetailViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route) {
                        popUpTo("taskDetail") { inclusive = true }
                    }
                }
            }

        }
    }

    Scaffold(
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(TaskDetailViewModel.TaskEvent.Submit)
                },
                modifier = Modifier
                    .padding(end = 16.dp, bottom = 80.dp),
                containerColor = Color(0xFF2196F3),
                shape = CircleShape
            ){
                Icon(Icons.Outlined.Done, contentDescription = "Save", tint = Color.White)
            }
        }
    ){padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(bottom = 80.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        "Edit Task",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = {
                        viewModel.onEvent(TaskDetailViewModel.TaskEvent.Submit)
                    }) {
                        Icon(
                            Icons.Outlined.Done,
                            contentDescription = "Save",
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Task details form
                Text("Title")
                TextField(
                    value = uiState.title,
                    onValueChange = {
                        viewModel.onEvent(TaskDetailViewModel.TaskEvent.UpdateTitle(it))
                                    },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0x142196F3),
                        unfocusedContainerColor = Color(0x142196F3),
                        focusedIndicatorColor = Color.Green,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Category")
                CategoryDropDownMenu(
                    selectedCategoryName = uiState.categoryName,
                    onCategorySelected = { id, name ->
                        viewModel.onEvent(TaskDetailViewModel.TaskEvent.UpdateCategory(id, name))
                    },
                    categories = categoryUiState.allCategories
                )


                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Time",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Date",
                        modifier = Modifier.weight(1f),

                        )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = uiState.dueTime,
                        onValueChange = {
                            viewModel.onEvent(TaskDetailViewModel.TaskEvent.UpdateDueTime(it))
                                        },
                        label = { Text("Due Time") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0x142196F3),
                            unfocusedContainerColor = Color(0x142196F3),
                            focusedIndicatorColor = Color.Green,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedLabelColor = Color.Transparent
                        ),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = uiState.dueDate,
                        onValueChange = {
                            viewModel.onEvent(TaskDetailViewModel.TaskEvent.UpdateDueDate(it))
                                        },
                        label = { Text("Due Date") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0x142196F3),
                            unfocusedContainerColor = Color(0x142196F3),
                            focusedIndicatorColor = Color.Green,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedLabelColor = Color.Transparent
                        ),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Periodicity",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color(0xFF2196F3)
                )

                PeriodicityDropDown(
                    selectedPeriodicity = uiState.periodicity,
                    onPeriodicitySelected = {
                        viewModel.onEvent(TaskDetailViewModel.TaskEvent.UpdatePeriodicity(it))
                    }
                )

                Text("Description")
                TextField(
                    value = uiState.notes,
                    onValueChange = {
                        viewModel.onEvent(TaskDetailViewModel.TaskEvent.UpdateNotes(it))
                    },
                    label = { Text("Notes") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0x142196F3),
                        unfocusedContainerColor = Color(0x142196F3),
                        focusedIndicatorColor = Color.Green,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Priority",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color(0xFF2196F3)
                )

                PrioritySection(
                    selectedPriority = uiState.priority,
                    onPriorityChange = {
                        viewModel.onEvent(TaskDetailViewModel.TaskEvent.UpdatePriority(it))
                    }
                )


            }

            // Bottom Navigation
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomNav2()
            }
        }
    }

}