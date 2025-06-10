package com.deepakjetpackcompose.mangaapp.presentation.component

import android.widget.Toast
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.AuthState
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MangaImagePager(mangaViewModel: MangaViewModel,navController: NavController,mangaList: List<MangaUiModel>) {
    if (mangaList.isEmpty()) return

    val configuration = LocalConfiguration.current
    val imageHeightDp = configuration.screenHeightDp.dp * 0.49f
    val context=LocalContext.current
    val authState=mangaViewModel.authState.collectAsState()

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
        val encodedUrl = URLEncoder.encode(manga.imgUrl, StandardCharsets.UTF_8.toString())
        val encodedTitle = URLEncoder.encode(manga.title, StandardCharsets.UTF_8.toString())
        val encodedDescription = URLEncoder.encode(manga.description, StandardCharsets.UTF_8.toString())
        val encodedId = URLEncoder.encode(manga.id, StandardCharsets.UTF_8.toString())

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
                                0.0f to Color(0xFF1D1D1D),
                                0.2f to Color.Transparent,
                                1.0f to Color(0xFF1D1D1D)
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
                            onClick = {
                                mangaViewModel.getMangaChapter(mangaId = manga.id)
                                navController.navigate(
                                    "${NavigationHelper.ChapterScreen.route}/$encodedUrl/$encodedTitle/$encodedDescription/$encodedId"
                                )
                            },
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
                            onClick = {
                                if(authState.value== AuthState.Authenticated) {
                                    mangaViewModel.addManga(
                                        manga = manga
                                    ) { success, msg ->
                                        if (success) {
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        }

                                    }
                                }else{
                                    Toast.makeText(context, "Please sign in to add", Toast.LENGTH_SHORT).show()
                                }
                            },
                            border = BorderStroke(width = 1.dp, color = Color.White),
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
                                    contentDescription = null, tint = Color.White,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(Modifier.width(10.dp))
                                Text("my list", color = Color.White
                                )



                            }
                        }
                    }
                }
            }
        }
    }
}
