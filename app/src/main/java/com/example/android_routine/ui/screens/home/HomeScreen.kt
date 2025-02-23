package com.example.android_routine.ui.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import com.example.android_routine.ui.screens.components.BottomNav
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.android_routine.ui.screens.components.CategoryItem
import com.example.android_routine.ui.screens.components.TaskItem


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()){
        Column( ) {

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = uiState.searchQuery,
                onValueChange = {viewModel.searchTasks(it)},
                placeholder = { Text(
                    text = "Search for Tasks, Events",
                    color = Color(0xFF333333),

                    )},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF333333).copy(alpha = 0.30f),
                        shape = RoundedCornerShape(10.dp)
                    ),
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
                CategoryItem(navController,"Personal", Icons.Outlined.Face, 0xFFFF5722)
                CategoryItem(navController,"House", Icons.Outlined.Home, 0xFF2196F3)
                CategoryItem(navController,"Shopping", Icons.Outlined.ShoppingCart, 0xFFFFC107)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CategoryItem(navController,"Work", Icons.Outlined.MailOutline, 0xFF333333)
                CategoryItem(navController,"Health", Icons.Outlined.FavoriteBorder, 0xFFFFA3A3)
                CategoryItem(navController,"See more", Icons.Outlined.Menu, 0xFF4246C9)
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
                        .clickable { navController.navigate(Screen.AllTasks.route)
                        })
            }

            Column(modifier = Modifier.weight(1f)) {

                LazyColumn {
                    items(
                        items = uiState.filteredTasks.filter { it.isToday },
                        key = { task -> task.id  }
                        ) { task ->
                        TaskItem(
                            task = task,
                            onTaskClick = {
                                navController.navigate(Screen.TaskDetail.createRoute(task.id)) },
                            onDelete = { viewModel.deleteTask(task.id) },
                            onRadioClick = {
                                viewModel.toggleTaskCompletion(task.id)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            BottomNav(navController)
        }


    }


}



