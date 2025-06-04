package com.deepakjetpackcompose.mangaapp.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.deepakjetpackcompose.mangaapp.presentation.component.ListOfFavourite
import com.deepakjetpackcompose.mangaapp.presentation.component.ListOfNewManga
import com.deepakjetpackcompose.mangaapp.presentation.component.ListOfTopAiring
import com.deepakjetpackcompose.mangaapp.presentation.component.ListOfUpdatedManga
import com.deepakjetpackcompose.mangaapp.presentation.component.MangaImagePager
import com.deepakjetpackcompose.mangaapp.presentation.component.MangaTopBar
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val mangaList =mangaViewModel.mangaList.collectAsState()
    val topAiredManga=mangaViewModel.getTopAired.collectAsState()
    val favouriteManga=mangaViewModel.getFavourite.collectAsState()
    val updatedManga=mangaViewModel.getUpdated.collectAsState()
    val newManga=mangaViewModel.getNew.collectAsState()
    val systemController = rememberSystemUiController()

    SideEffect {
        systemController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )

        systemController.setNavigationBarColor(
            color = Color.Black,
            darkIcons = false
        )
    }
    LaunchedEffect(Unit) {
        mangaViewModel.getAllManga()
        mangaViewModel.getTopAiredManga()
        mangaViewModel.getFavouriteManga()
        mangaViewModel.getNewManga()
        mangaViewModel.getUpdatedManga()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()
            )
            .background(Color(0xFF1D1D1D))
            .navigationBarsPadding()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Background image slider
            MangaImagePager(mangaList = mangaList.value)

            // Transparent TopAppBar
            MangaTopBar(modifier = Modifier.statusBarsPadding())

        }
        Spacer(Modifier.height(30.dp))
        //top Airing manga
        ListOfTopAiring(mangaViewModel = mangaViewModel,topAiredManga = topAiredManga.value,navController=navController,modifier= Modifier.padding(start = 20.dp))

        Spacer(Modifier.height(20.dp))
        //top favourite manga
        ListOfFavourite(mangaViewModel = mangaViewModel,favourite = favouriteManga.value,navController=navController,modifier= Modifier.padding(start = 20.dp))
        Spacer(Modifier.height(20.dp))

        ListOfNewManga(mangaViewModel = mangaViewModel,newly = newManga.value,navController=navController,modifier= Modifier.padding(start = 20.dp))
        Spacer(Modifier.height(20.dp))

        ListOfUpdatedManga(mangaViewModel = mangaViewModel,updated = updatedManga.value,navController=navController,modifier= Modifier.padding(start = 20.dp))
        Spacer(Modifier.height(15.dp))
    }

}