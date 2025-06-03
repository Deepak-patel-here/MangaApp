package com.deepakjetpackcompose.mangaapp.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.deepakjetpackcompose.mangaapp.presentation.component.MangaImagePager
import com.deepakjetpackcompose.mangaapp.presentation.component.MangaTopBar
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(mangaViewModel: MangaViewModel,modifier: Modifier = Modifier) {
    val mangaList =mangaViewModel.mangaList.collectAsState()
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
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()
            )
            .background(Color.Black)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image slider
            MangaImagePager(mangaList = mangaList.value)

            // Transparent TopAppBar
            MangaTopBar(modifier = Modifier.statusBarsPadding())

        }
    }

}