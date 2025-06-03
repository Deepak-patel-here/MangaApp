package com.deepakjetpackcompose.mangaapp.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import com.deepakjetpackcompose.mangaapp.R


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MangaImagePager(mangaList: List<MangaUiModel>) {
    if (mangaList.isEmpty()) return

    val configuration = LocalConfiguration.current
    val imageHeightDp = configuration.screenHeightDp.dp * 0.49f

    val pagerState = rememberPagerState()

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

            AsyncImage(
                model = manga.imgUrl,
                contentDescription = manga.title,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to Color.Black.copy(alpha = 0.3f),
                                0.2f to Color.Transparent,
                                1.0f to Color.Black
                            )
                        )
                    )
            )

            // Text and Button at bottom start
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, bottom = 16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text(
                        text = manga.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.width(200.dp)
                    )
                    Spacer(Modifier.height(10.dp))
                    Row {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .width(100.dp)
                                .height(35.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text("Read")
                                Spacer(Modifier.width(10.dp))
                                Icon(
                                    painter = painterResource(R.drawable.read),
                                    contentDescription = null, tint = Color.White,
                                    modifier = Modifier.size(25.dp)
                                )

                            }
                        }

                        Spacer(Modifier.width(20.dp))

                        OutlinedButton(
                            onClick = {},
                            border = BorderStroke(width = 1.dp, color = Color.Red),
                            modifier = Modifier
                                .wrapContentSize()
                                .height(35.dp),
                        ) {
                            Row(
                                modifier = Modifier.wrapContentSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null, tint = Color.Red,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(Modifier.width(10.dp))
                                Text("my list", color = Color.Red
                                )



                            }
                        }
                    }
                }
            }
        }
    }
}
