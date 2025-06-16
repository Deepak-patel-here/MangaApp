package com.deepakjetpackcompose.mangaapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepakjetpackcompose.mangaapp.R


@Composable
fun MangaBottomBar(isSelected: MutableState<Int>, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(85.dp)
            .background(Color(0xFF1D1D1D))
        ,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        NavIcon(img = R.drawable.manga_home_icon, title = "Home", isSelected = isSelected, value = 1)
        NavIcon(img = R.drawable.schedule_manga_icon, title = "Schedule",isSelected = isSelected, value = 2)
        NavIcon(img = R.drawable.manga_list_icon, title = "My List",isSelected = isSelected, value = 3)
        NavIcon(img = R.drawable.manga_profile_icon, title = "Profile",isSelected = isSelected, value = 4)

    }

}


@Composable
fun NavIcon(isSelected: MutableState<Int>, value: Int, img: Int, title: String, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(vertical = 5.dp)
            .clickable(onClick = {
                isSelected.value=value
            }),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(img),
            contentDescription = null,
            tint = if(isSelected.value==value) Color.Red else Color.LightGray,
            modifier = Modifier.size(20.dp)
        )

        Spacer(Modifier.height(10.dp))
        Text(title, color = if(isSelected.value==value) Color.Red else Color.LightGray, fontSize = 14.sp)
    }

}