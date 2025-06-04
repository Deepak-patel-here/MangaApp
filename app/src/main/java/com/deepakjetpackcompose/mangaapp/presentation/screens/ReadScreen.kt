package com.deepakjetpackcompose.mangaapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ReadScreen(
    mangaViewModel: MangaViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val chapterImages = mangaViewModel.chapterImages.collectAsState()

    val chapter = chapterImages.value.chapter
    val baseUrl = chapterImages.value.baseUrl
    val systemController = rememberSystemUiController()

    SideEffect {
        systemController.setStatusBarColor(
            color = Color(0xFF1D1D1D),
            darkIcons = false
        )

        systemController.setNavigationBarColor(
            color = Color.Black,
            darkIcons = false
        )
    }
    if (chapter == null || chapter.data.isNullOrEmpty() || baseUrl?.isBlank() == true) {
        Column(
            modifier = modifier.fillMaxSize().background(Color(0xFF1D1D1D)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize().background(Color(0xFF1D1D1D))
        ) {
            // Move top bar here
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1D1D1D))) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                }
                Spacer(Modifier.width(30.dp))
                Text(
                    text = "${pagerState.currentPage + 1} / ${chapter.data.size}",
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            HorizontalPager(
                count = chapter.data.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val img = chapter.data[page]
                val imgUrl = "$baseUrl/data/${chapter.hash}/$img"

                AsyncImage(
                    model = imgUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            LinearProgressIndicator(
                progress = (pagerState.currentPage + 1).toFloat() / chapter.data.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Red
            )
        }
    }

}




