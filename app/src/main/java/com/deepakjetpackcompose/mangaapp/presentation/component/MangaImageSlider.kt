package com.deepakjetpackcompose.mangaapp.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MangaImageSlider(mangaList: List<MangaUiModel>) {
    if (mangaList.isEmpty()) return

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val imageHeightDp = configuration.screenHeightDp.dp * 0.47f
    val imageHeightPx = with(density) { imageHeightDp.toPx() }

    val imageLoader = ImageLoader.Builder(context)
        .memoryCache { MemoryCache.Builder(context).maxSizePercent(0.25).build() }
        .crossfade(true)
        .logger(DebugLogger())
        .build()

    var currentIndex by remember { mutableStateOf(0) }
    var swipeDirection by remember { mutableStateOf(1) } // 1 = next (slide from right), -1 = previous (slide from left)

    // Auto-slide coroutine
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentIndex = (currentIndex + 1) % mangaList.size
            swipeDirection = 1
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeightDp)
            .pointerInput(mangaList) {
                detectHorizontalDragGestures(
                    onDragEnd = { /* no-op */ },
                    onHorizontalDrag = { change, dragAmount ->
                        // Detect swipe when dragAmount is big enough (change in X)
                        if (dragAmount > 100) {
                            // Swipe right: previous image
                            currentIndex = if (currentIndex == 0) mangaList.lastIndex else currentIndex - 1
                            swipeDirection = -1
                        } else if (dragAmount < -100) {
                            // Swipe left: next image
                            currentIndex = (currentIndex + 1) % mangaList.size
                            swipeDirection = 1
                        }
                        change.consume()
                    }
                )
            }
    ) {
        // Key on currentIndex to trigger AnimatedContent recomposition and animation
        AnimatedContent(
            targetState = currentIndex,
            transitionSpec = {
                if (swipeDirection > 0) {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(durationMillis = 400)
                    ) with slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(durationMillis = 400)
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(durationMillis = 400)
                    ) with slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(durationMillis = 400)
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { index ->
            val url = mangaList[index].imgUrl
            if (url != null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(url)
                                .crossfade(true)
                                .build(),
                            imageLoader = imageLoader
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
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
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

