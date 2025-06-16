package com.deepakjetpackcompose.mangaapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.deepakjetpackcompose.mangaapp.presentation.component.MangaBottomBar
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MyApp(
    mangaViewModel: MangaViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val isSelected = rememberSaveable { mutableIntStateOf(1) }


    Scaffold(bottomBar = {
        MangaBottomBar(
            isSelected = isSelected,
            modifier = Modifier
                .navigationBarsPadding()
                .background(Color(0xFF1D1D1D)))
    },
        containerColor = Color(0xFF1D1D1D)) { innerPadding ->
        val contentModifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        when (isSelected.intValue) {
            1 -> HomeScreen(
                mangaViewModel = mangaViewModel,
                navController = navController,
                modifier = contentModifier
            )

            2 -> ScheduleScreen(mangaViewModel = mangaViewModel, navController = navController)
            3 -> MyListScreen(mangaViewModel = mangaViewModel, navController = navController)
            4 -> ProfileScreen(mangaViewModel = mangaViewModel, navController = navController)
        }
    }
}