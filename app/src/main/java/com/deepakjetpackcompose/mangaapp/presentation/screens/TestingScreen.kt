package com.deepakjetpackcompose.mangaapp.presentation.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel

@Composable
fun TestingScreen(mangaViewModel: MangaViewModel,modifier: Modifier = Modifier) {
    val mangaList =mangaViewModel.mangaList.collectAsState()
    LaunchedEffect(Unit) {
        mangaViewModel.getAllManga()
    }

    LazyColumn {
        items (mangaList.value){manga->
            Row (modifier = Modifier.fillMaxWidth().padding(20.dp)){

            }
        }
    }


}