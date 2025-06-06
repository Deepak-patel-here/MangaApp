package com.deepakjetpackcompose.mangaapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun ReadScreen(chapterId:String,
    mangaViewModel: MangaViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val chapterImages = mangaViewModel.chapterImages.collectAsState()
    val allChapters = mangaViewModel.getChapters.collectAsState() // Get the chapter list
    val chapter = chapterImages.value.chapter
    val baseUrl = chapterImages.value.baseUrl
    val systemController = rememberSystemUiController()
    val scope = rememberCoroutineScope()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    LaunchedEffect(chapterId) {
        scope.launch {
            pagerState.scrollToPage(0)
        }
    }

    SideEffect {
        systemController.setStatusBarColor(Color(0xFF1D1D1D), darkIcons = false)
        systemController.setNavigationBarColor(Color.Black, darkIcons = false)
    }

    if (chapter == null || chapter.data.isNullOrEmpty() || baseUrl.isNullOrBlank() || chapter.dataSaver.isNullOrEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF1D1D1D)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(150.dp)
                )
            }
        }
    } else {
        val currentChapterIndex = allChapters.value.indexOfFirst { it.id == chapterId }
        val currentChapterNo=allChapters.value.getOrNull(currentChapterIndex)?.chapter
        val hasNextChapter = currentChapterIndex != -1 && currentChapterIndex + 1 < allChapters.value.size
        val nextChapter = if (hasNextChapter) allChapters.value[currentChapterIndex + 1] else null

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF1D1D1D))
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1D1D1D))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                }
                Spacer(Modifier.width(30.dp))
                Text(
                    text = "Chapter ${currentChapterNo ?: "?"} | ${pagerState.currentPage + 1} / ${chapter.data.size}",
                    color = Color.White,
                )
            }

            HorizontalPager(
                count = chapter.data.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val img = chapter.data[page]
                val imgUrl = "$baseUrl/data/${chapter.hash}/$img"

                Column(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = imgUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentScale = ContentScale.FillBounds
                    )
                    if (page == chapter.data.lastIndex && hasNextChapter) {
                        Spacer(modifier = Modifier.height(16.dp))
                        androidx.compose.material3.Button(
                            onClick = {
                                mangaViewModel.getChapterImages(nextChapter!!.id)
                                navController.navigate("${NavigationHelper.ReadScreen.route}/${nextChapter!!.id}") {
                                    popUpTo("${NavigationHelper.ReadScreen.route}/$chapterId") {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Read Next Chapter", fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
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





