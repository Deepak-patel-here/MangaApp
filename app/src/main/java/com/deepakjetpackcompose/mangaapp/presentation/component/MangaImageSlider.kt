package com.deepakjetpackcompose.mangaapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MangaImagePager(mangaList: List<MangaUiModel>) {
    if (mangaList.isEmpty()) return

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val imageHeightDp = configuration.screenHeightDp.dp * 0.47f
    val density = LocalDensity.current
    val imageHeightPx = with(density) { imageHeightDp.toPx() }

    val pagerState = rememberPagerState()

    // Auto-scroll coroutine
    LaunchedEffect(pagerState.currentPage) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % mangaList.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    HorizontalPager(
        count = mangaList.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeightDp)
    ) { page ->

        val manga = mangaList[page]

        Box(modifier = Modifier.fillMaxSize()) {
            val painter = rememberAsyncImagePainter(model = manga.imgUrl)
            val painterState = painter.state
            AsyncImage(
                model = manga.imgUrl,
                contentDescription = manga.title,
                modifier = Modifier.height(imageHeightDp).fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = null,
                error = null,
                onLoading = {
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = imageHeightPx * 0.6f,
                            endY = imageHeightPx
                        )
                    )
            )
        }
    }
}
