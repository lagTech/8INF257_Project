package com.example.android_routine.ui.screens.addtask

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import com.example.android_routine.ui.screens.components.BottomNav
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.ui.screens.components.CategoryDropDownMenu
import com.example.android_routine.ui.screens.components.DateItem
import com.example.android_routine.ui.screens.components.DateTimePickers
import com.example.android_routine.ui.screens.components.NotesCard
import com.example.android_routine.ui.screens.components.PeriodicityDropDown
import com.example.android_routine.ui.screens.components.PrioritySection


@Composable
fun AddTaskScreen(navController: NavController, viewModel: AddTaskViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){

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
                onValueChange = { viewModel.updateTitle(it) },
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

            Text("Category", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 3.dp), color = Color(0xFF2196F3),)

            //
            CategoryDropDownMenu()

            Text("Date", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 5.dp), color = Color(0xFF2196F3))

            DateTimePickers(
                context = LocalContext.current,
                onDateSelected = { viewModel.updateDueDate(it) },
                onTimeSelected = { viewModel.updateDueTime(it) }
            )


            Text("Priority", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 5.dp), color = Color(0xFF2196F3))

            PrioritySection()

            Text("Periodicity", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 5.dp), color = Color(0xFF2196F3))

            PeriodicityDropDown(viewModel)

            Text("Notes", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFF2196F3))

            NotesCard(uiState.notes, viewModel::updateNotes)

            Scaffold (
                floatingActionButton = {

                    FloatingActionButton(
                        onClick = {
                            if (viewModel.addTask()){
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier
                            .padding(vertical = 60.dp)
                            .offset(y = (-28).dp),
                        containerColor = Color(0xFF2196F3),
                        shape = androidx.compose.foundation.shape.CircleShape,
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

            ) {
                    paddingValues ->
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues))

            }

        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ){
            BottomNav2()
        }
    }


}




