package com.example.android_routine.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import com.example.android_routine.view.components.BottomNav
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android_routine.model.Task
import com.example.android_routine.view.components.BottomNav
import com.example.android_routine.view.components.CategoryItem
import com.example.android_routine.view.components.TaskItem
import com.example.android_routine.viewmodel.TaskViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: TaskViewModel) {
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            TextField(
                value = "", onValueChange = {},
                placeholder = { Text(
                    text = "Search for Tasks, Events",
                    color = Color(0xFF333333),

                    )},
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFF333333).copy(alpha = 0.30f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0x142196F3),
                    unfocusedContainerColor = Color(0x142196F3),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                ),

                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint  = Color(0xFF333333)
                    )
                }
            )

            Text("Categories", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CategoryItem("Personal", Icons.Outlined.Face, 0xFFFF5722)
                CategoryItem("House", Icons.Outlined.Home, 0xFF2196F3)
                CategoryItem("Shopping", Icons.Outlined.ShoppingCart, 0xFFFFC107)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CategoryItem("Work", Icons.Outlined.MailOutline, 0xFF333333)
                CategoryItem("Health", Icons.Outlined.FavoriteBorder, 0xFFFFA3A3)
                CategoryItem("See more", Icons.Outlined.Menu, 0xFF4246C9)
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Today's tasks", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = "See all",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF2196F3),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("allTasks") })
            }

            val todayTasks = viewModel.getTodayTasks()
            LazyColumn {
                items(todayTasks) { task ->
                    TaskItem(task)
                }
            }

        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ){
            BottomNav()
        }
    }


}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
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
    HomeScreen(
        navController = mockNavController,
        viewModel = MockTaskViewModel()
    )
}

