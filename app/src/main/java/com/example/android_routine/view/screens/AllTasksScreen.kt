package com.example.android_routine.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.android_routine.model.Task
import com.example.android_routine.view.components.BottomNav
import com.example.android_routine.view.components.BottomNav2
import com.example.android_routine.view.components.CategoryItem
import com.example.android_routine.view.components.TaskItem
import com.example.android_routine.view.components.TaskItem2
import com.example.android_routine.viewmodel.TaskViewModel

@Composable
fun AllTasksScreen(navController: NavController, viewModel: TaskViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize().padding(16.dp))  {
            Text("All tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color=Color(0xFF2196F3),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text("Overdue",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color=Color(0xFFFF5722),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            TaskItem2()
            Text("All tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color=Color(0xFF2196F3),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            TaskItem2()
            TaskItem2()
            TaskItem2()
            TaskItem2()
            TaskItem2()
            TaskItem2()

            Scaffold (
                floatingActionButton = {

                    FloatingActionButton(
                        onClick = { },
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
                                Icons.Default.Add,
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
@Preview(showBackground = true)
@Composable
fun AllTasksScreen() {
    // Create a mock NavController
    val mockNavController = rememberNavController()

    // Create a mock TaskViewModel
    class MockTaskViewModel : TaskViewModel() {
        override fun getTodayTasks(): List<Task> {
            return listOf(
                Task(id = 1, title = "Meeting with team", category = "Work", isCompleted = false, dueTime = "12:00 pm", notes = ""),
                Task(id = 2, title = "Gym workout", category = "Health", isCompleted = true, dueTime = "10:00 pm", notes = ""),
                Task(id = 3, title = "Read a book", category = "Personal", isCompleted = false, isToday = false, dueTime = "4:00pm", notes = "")
            )
        }
    }

    // Use the mock dependencies in the preview
    AllTasksScreen(
        navController = mockNavController,
        viewModel = MockTaskViewModel()
    )
}