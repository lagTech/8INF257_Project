import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddCategoryBottomSheet(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    newCategoryName: String,
    onNameChange: (String) -> Unit,
    onCreateCategory: () -> Unit
) {
    if (showDialog) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable { onDismiss() }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .fillMaxWidth()
                    .height(174.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Drag Handle
                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp)
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // TextField
                    TextField(
                        value = newCategoryName,
                        onValueChange = onNameChange,
                        placeholder = { Text("Category Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Green,
                            unfocusedLabelColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
//                            focusedIndicatorColor = Color(0xFF2196F3),
                            unfocusedIndicatorColor = Color(0xFF2196F3).copy(alpha = 0.5f)
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Create Button
                    Button(
                        onClick = onCreateCategory,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2196F3)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Create Category", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ViewScreen(){
    AddCategoryBottomSheet(
        onDismiss = {},
        onCreateCategory = {},
        onNameChange = {},
        showDialog = true,
        newCategoryName = ""
    )
}

