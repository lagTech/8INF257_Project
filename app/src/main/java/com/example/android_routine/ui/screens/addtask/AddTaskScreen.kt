package com.example.android_routine.ui.screens.addtask


import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite

import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android_routine.data.model.Task
import com.example.android_routine.ui.screens.components.BottomNav2
import com.example.android_routine.ui.screens.components.TaskItem
import com.example.android_routine.ui.screens.components.CategoryItem

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

import com.example.android_routine.ui.screens.allcategories.CategoryViewModel
import com.example.android_routine.ui.screens.components.CategoryDropDownMenu
import com.example.android_routine.ui.screens.components.DateTimePickers
import com.example.android_routine.ui.screens.components.NotesCard
import com.example.android_routine.ui.screens.components.PeriodicityDropDown
import com.example.android_routine.ui.screens.components.PrioritySection
import com.example.android_routine.utils.RoutinesUtils


@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: AddTaskViewModel,
    categoryViewModel: CategoryViewModel) {

    val categoryUiState by categoryViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val utils = remember { RoutinesUtils() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is AddTaskViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is AddTaskViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route){
                        popUpTo(Screen.AddTask.route){inclusive = true}
                    }
                }
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddTaskViewModel.TaskEvent.Submit(context, utils), context)
                },
                modifier = Modifier
                    .padding(end = 16.dp, bottom = 80.dp),
                containerColor = Color(0xFF2196F3),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Check, contentDescription = "Add", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {


                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF2196F3)
                        )
                    }

                }

                Text(
                    "Add task",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2196F3),
                    modifier = Modifier.padding(vertical = 8.dp)
                )


                TextField(
                    value = uiState.title,
                    onValueChange = { viewModel.onEvent(AddTaskViewModel.TaskEvent.UpdateTitle(it), context) },
                    label = { Text("Title") },
                    isError = uiState.isError,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0x142196F3),
                        unfocusedContainerColor = Color(0x142196F3),
                        focusedIndicatorColor = Color.Green,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp),
                    supportingText = { uiState.errorMessage?.let { Text(it, color = Color.Red) } }
                )

                Text(
                    "Category",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 3.dp),
                    color = Color(0xFF2196F3),
                )

                //
                CategoryDropDownMenu(
                    selectedCategoryName = uiState.categoryName,
                    onCategorySelected = { id, name ->
                        viewModel.onEvent(AddTaskViewModel.TaskEvent.UpdateCategory(id, name), context )
                    },
                    categories = categoryUiState.allCategories
                )

                Text(
                    "Date",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = Color(0xFF2196F3)
                )

                DateTimePickers(
                    context = LocalContext.current,
                    onDateSelected = {
                        viewModel.onEvent(AddTaskViewModel.TaskEvent.UpdateDueDate(it), context)
                    },
                    onTimeSelected = {
                        viewModel.onEvent(AddTaskViewModel.TaskEvent.UpdateDueTime(it), context)
                    }
                )



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
                        viewModel.onEvent(AddTaskViewModel.TaskEvent.UpdatePriority(it), context)
                    }
                )

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
                        viewModel.onEvent(AddTaskViewModel.TaskEvent.UpdatePeriodicity(it), context)
                    }
                )

                Text(
                    "Notes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0xFF2196F3)
                )

                NotesCard(
                    notes = uiState.notes,
                    onNotesChange = {
                        viewModel.onEvent(AddTaskViewModel.TaskEvent.UpdateNotes(it), context )
                    }
                )


                Scaffold(
                    floatingActionButton = {

                        FloatingActionButton(
                            onClick = {
                                viewModel.onEvent(AddTaskViewModel.TaskEvent.Submit(context, utils), context)
                            },
                            modifier = Modifier
                                .padding(vertical = 60.dp)
                                .offset(y = (-28).dp),
                            containerColor = Color(0xFF2196F3),
                            shape = CircleShape,
                            elevation = FloatingActionButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp
                            ),
                            content = {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "Add",
                                    tint = Color.White
                                )
                            }
                        )
                    }

                ) { paddingValues ->
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues))

                }

            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                BottomNav2()
            }
        }


    }}




