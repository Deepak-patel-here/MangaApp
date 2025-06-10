package com.deepakjetpackcompose.mangaapp.presentation.screens

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
fun MyApp(mangaViewModel: MangaViewModel,navController: NavController,modifier: Modifier = Modifier) {
    val isSelected= rememberSaveable { mutableIntStateOf(1) }
    val systemController = rememberSystemUiController()

    SideEffect {
        systemController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )

        systemController.setNavigationBarColor(
            color = Color(0xFF1D1D1D),
            darkIcons = false
        )
    }
    Scaffold(bottomBar = { MangaBottomBar(isSelected = isSelected, modifier = Modifier.navigationBarsPadding()) }) { innerPadding->
        val contentModifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        when(isSelected.intValue){
            1 ->HomeScreen(mangaViewModel = mangaViewModel, navController = navController, modifier = contentModifier)
            2->ScheduleScreen()
            3->MyListScreen(mangaViewModel=mangaViewModel,navController=navController)
            4->ScheduleScreen()
        }
    }
}