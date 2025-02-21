package com.example.android_routine.view.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_routine.R

val list = listOf(
    R.drawable.ic_home,
    R.drawable.ic_search,
    R.drawable.ic_trending,
    R.drawable.ic_notif,
    R.drawable.ic_setting,
)

val list2 = listOf(
    NavItem(
        R.drawable.ic_home,
        "home"
    ),
    NavItem(
        R.drawable.ic_calendar,
        "calender"
    ),
    NavItem(
        R.drawable.ic_add,
        "add"
    ),
    NavItem(
        R.drawable.ic_notif,
        "notification"
    ),
    NavItem(
        R.drawable.ic_setting,
        "settings"
    ),

)

data class  NavItem(
    @DrawableRes val icon: Int,
    val title: String
)

@Preview
@Composable
fun Display1(){
    Column {
        NavBar1(0,list)
        Spacer(Modifier.height(12.dp))
        NavBar2(0, list2)
    }
}

@Composable
fun NavBar1(
    defaultSelectedIndex: Int = 0,
    list: List<Int>
){
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = Color.White
    ){
        var selectedIndex by remember {
            mutableStateOf(defaultSelectedIndex)
        }

        Row(Modifier.fillMaxSize()){
            list.forEachIndexed{ index, icon ->

                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment =  Alignment.Center
                ){
                    Box(
                        Modifier
                            .size(40.dp)
                            .clickable {
                                selectedIndex = index
                            }
                            .background(
                                if (selectedIndex == index) MaterialTheme.colorScheme.primary
                                else Color.Transparent,
                                RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            painterResource(icon),
                            null,
                            Modifier.size(24.dp),
                            tint = if (selectedIndex == index) Color.White else Color.Gray
                        )
                    }
                }


            }
        }

    }

}

@Composable
fun NavBar2(
    defaultSelectedIndex: Int = 0,
    list: List<NavItem>
){
    Box(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
    ){
        var selectedIndex by remember {
            mutableStateOf(defaultSelectedIndex)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ){
            list.forEachIndexed { index, navItem ->
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(11f)
                        .clickable {
                            selectedIndex = index
                        },
                    contentAlignment = Center
                ){
                    Column(
                        if (selectedIndex == index) Modifier.offset(y = (-8).dp)
                        else Modifier,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Box(
                            Modifier.background(
                                if(selectedIndex == index) MaterialTheme.colorScheme.primary
                                else Color.Transparent,
                                CircleShape
                            )
                                .size(36.dp),
                            contentAlignment = Center
                        ){
                            Icon(
                                painterResource(navItem.icon),
                                null,
                                Modifier.size(24.dp),
                                tint = if (selectedIndex == index) Color.White else Color.Gray
                            )
                        }

                        AnimatedVisibility(selectedIndex == index) {
                            Text(
                                navItem.title,
                                modifier = Modifier.padding(top = 4.dp),
                                color = Color.DarkGray,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}