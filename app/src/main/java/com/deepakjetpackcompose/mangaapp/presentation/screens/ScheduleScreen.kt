package com.deepakjetpackcompose.mangaapp.presentation.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.presentation.component.ListTopBar
import com.deepakjetpackcompose.mangaapp.presentation.component.ScheduleBar
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import java.time.LocalDate

@Composable
fun ScheduleScreen(navController: NavController,mangaViewModel: MangaViewModel, modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val mangaData=mangaViewModel.upcomingManga.collectAsState()


    Scaffold(topBar = {
        ListTopBar(
            title = "Schedule",
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 20.dp),
            onSearch = { navController.navigate(NavigationHelper.SearchScreen.route) },
        )
    }, containerColor = Color(0xFF1D1D1D)) { innerPadding ->
        Spacer(Modifier.height(30.dp))
        Column (modifier= Modifier.padding(innerPadding).background(Color(0xFF1D1D1D))){
            ScheduleBar(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )

            Divider(color = Color.Red, thickness = 4.dp, modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()){

                items (mangaData.value){manga->
                    Text(manga.title, modifier = Modifier.padding(20.dp), color = Color.White)
                }
            }



        }
    }
    
}