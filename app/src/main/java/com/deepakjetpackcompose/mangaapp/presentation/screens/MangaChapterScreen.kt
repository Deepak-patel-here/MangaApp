package com.deepakjetpackcompose.mangaapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.presentation.component.ChaptersComponent
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MangaChapterScreen(
    mangaViewModel: MangaViewModel,
    mangaId: String,
    imgUrl: String,
    title: String,
    desc: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val getChapters = mangaViewModel.getChapters.collectAsState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
    val context= LocalContext.current
    val isLoading=mangaViewModel.isLoading.collectAsState()
    val chapterError by mangaViewModel.chapterError.collectAsState()

    LaunchedEffect(chapterError) {
        chapterError?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D1D1D))
            .padding(horizontal = 20.dp)
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button row
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        // Manga Cover
        item {
            Card(
                modifier = Modifier
                    .height(170.dp)
                    .width(130.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.elevatedCardElevation(5.dp)
            ) {
                AsyncImage(
                    model = imgUrl,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        // Title
        item {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        // Buttons
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if(getChapters.value.isEmpty()){
                            Toast.makeText(context, "Sorry, but the chapter is unavailable", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val id= URLEncoder.encode(getChapters.value[0].id,StandardCharsets.UTF_8.toString())
                            mangaViewModel.getChapterImages(getChapters.value[0].id)
                            navController.navigate("${NavigationHelper.ReadScreen.route}/$id")
                        }
                    },
                    modifier = Modifier
                        .width(100.dp)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Read", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(10.dp))
                   Icon(
                        painter = painterResource(R.drawable.read),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(Modifier.width(30.dp))

                OutlinedButton(
                    onClick = {},
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier.height(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text("My List", color = Color.White,fontWeight = FontWeight.Bold)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        // Description
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2D2D2D))
            ) {
                Text(
                    desc,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    modifier = Modifier.padding(10.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        // Chapters Header
        item {
            Text(
                "Chapters:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }

        // Loading or list
        if (isLoading.value) {
            item {
                if(chapterError.isNullOrEmpty()) {
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

            }
        } else {
            items(getChapters.value) { item ->
                val id= URLEncoder.encode(item.id,StandardCharsets.UTF_8.toString())
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            mangaViewModel.getChapterImages(item.id)
                            navController.navigate("${NavigationHelper.ReadScreen.route}/$id")
                        }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Chapter: ${item.chapter ?: 0}",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Spacer(Modifier.height(7.dp))
            }
        }
    }
}
