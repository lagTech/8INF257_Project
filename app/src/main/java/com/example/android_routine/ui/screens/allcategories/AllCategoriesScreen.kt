package com.example.android_routine.ui.screens.allcategories

import AddCategoryBottomSheet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_routine.ui.screens.components.BottomNav2
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AllCategoriesScreen(
    navController: NavController,
    viewModel: CategoryViewModel
) {
    // State for controlling the modal visibility
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    // State for the new category name
    var newCategoryName by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = uiState.query,
                onValueChange = {viewModel.onEvent(CategoryViewModel.CategoryEvent.UpdateQuery(it))},
                placeholder = {
                    Text(
                        text = "Search for Categories",
                        color = Color(0xFF333333),

                        )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(10.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0x142196F3),
                    unfocusedContainerColor = Color(0x142196F3),
                    focusedIndicatorColor = Color.Green,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),

                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color(0xFF333333)
                    )
                }
            )

            Text(
                "Categories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
                LazyColumn {
                    items(
                        items = uiState.filteredCategories,
                        key = { it.id!! }
                    ) {category ->
                        TextField(
                            value = category.name,
                            onValueChange = {},

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0x142196F3),
                                unfocusedContainerColor = Color(0x142196F3),
                                focusedIndicatorColor = Color.Green,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                        )
                    }

                }
            }

            // Bottom Navigation
            BottomNav2()
        }

        FloatingActionButton(
            onClick = { showAddCategoryDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 100.dp),
            containerColor = Color(0xFF2196F3),
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }

        if (showAddCategoryDialog){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { showAddCategoryDialog = false }
            )
        }

        AnimatedVisibility(
            visible = showAddCategoryDialog,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                AddCategoryBottomSheet(
                    showDialog = showAddCategoryDialog,
                    onDismiss = { showAddCategoryDialog = false },
                    newCategoryName = newCategoryName,
                    onNameChange = { newCategoryName = it },
                    onCreateCategory = {
                        viewModel.onEvent(CategoryViewModel.CategoryEvent.UpdateNewCategoryName(newCategoryName))
                        viewModel.onEvent(CategoryViewModel.CategoryEvent.AddCategory)

                        showAddCategoryDialog = false
                        newCategoryName = ""
                    }
                )
            }
        }


    }
}

