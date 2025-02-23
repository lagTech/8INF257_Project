package com.example.android_routine.view.screens

// package com.example.android_routine.view.screens

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
import com.example.android_routine.model.Task
import com.example.android_routine.view.components.BottomNav2
import com.example.android_routine.view.components.CategoryItem
import com.example.android_routine.view.components.TaskItem
import com.example.android_routine.viewmodel.TaskViewModel

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily


@Composable
fun CategoryDropDownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Work") }

    val options = listOf("Work", "Personal", "Shopping", "Health")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(16.dp)
            .background(color = Color(0x142196F3)) // Ensures background color
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth().border(
                width = 1.dp,
                color = Color(0xFF2196F3),
                shape = RoundedCornerShape(10.dp)
            ).padding(vertical = 1.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Corrected from 0x142196F3 (invalid color)
                contentColor = Color.LightGray   // Ensures text is visible
            )
        ) {
            Text(selectedOption, modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Arrow",
                modifier = Modifier.size(27.dp),
                tint = Color.LightGray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x142196F3)) // Fix dropdown background
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = Color.LightGray) }, // Ensures black text
                    onClick = {
                        selectedOption = option
                        expanded = false
                    }
                )
            }
        }
    }

}

@Composable
fun DateItem(name: String, icon: ImageVector, color: Long) {
    Row(
        modifier = Modifier
            .padding(3.dp)
            .size(width = 150.dp, height = 50.dp),
        //.padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon,
            contentDescription = name,
            modifier = Modifier.size(27.dp),
            tint = Color(color)
        )
        Text(
            text = name,
            color = Color.LightGray,
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 2.dp)
        )

    }
}

@Composable
fun PrioritySection() {
    var selectedOption by remember { mutableStateOf("Moyenne") }

    val options = listOf("Faible", "Moyenne", "Elevee")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .size(width = 150.dp, height = 50.dp),
        horizontalArrangement = Arrangement.Start, // Aligns items to the left
        verticalAlignment = Alignment.CenterVertically // Aligns items vertically
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .clickable { selectedOption = option } // Click anywhere to select
                    .padding(end = 12.dp), // Spacing between items
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = (selectedOption == option),
                    onClick = { selectedOption = option },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF2196F3),
                        unselectedColor = Color.LightGray
                    )
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 1.dp),
                    color = Color.LightGray
                )
            }
        }
    }
}



@Composable
fun AddTaskScreen(navController: NavController, viewModel: TaskViewModel) {
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            Text(
                "Add task",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            TextField(
                value = "", onValueChange = {},
                placeholder = { Text(
                    text = "Finish Report",
                    color = Color.LightGray,

                    )},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0x142196F3)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF2196F3),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 2.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0x142196F3),
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent, //
                    unfocusedLabelColor = Color.Transparent
                ),

                singleLine = true
            )

            Text("Category", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 3.dp), color = Color(0xFF2196F3),)

            //
            CategoryDropDownMenu()

            Text("Date", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFF2196F3))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                DateItem("Set due date", Icons.Outlined.DateRange, 0xFFFFC107)
                DateItem("Set Time", Icons.Outlined.Notifications, 0xFFFF5722)
            }

            Text("Priority", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFF2196F3))

            PrioritySection()

            Text("Reminder", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFF2196F3))
            Row(
                modifier = Modifier
                    .padding(3.dp)
                    .size(width = 150.dp, height = 50.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Set Reminder",
                    modifier = Modifier.size(27.dp),
                    tint = Color(0xFFFF5722)
                )
                Text(
                    text = "Set Reminder",
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 1.dp)
                )

            }

            Text("Notes", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFF2196F3))

            TextField(
                value = "", onValueChange = {},
                placeholder = { Text(
                    text = "Make sure to research from internet",
                    color = Color.LightGray,

                    )},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0x142196F3)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF2196F3),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 1.dp, vertical = 1.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent, //
                    unfocusedLabelColor = Color.Transparent
                ),

                singleLine = true
            )

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

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
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
    AddTaskScreen(
        navController = mockNavController,
        viewModel = MockTaskViewModel()
    )
}



