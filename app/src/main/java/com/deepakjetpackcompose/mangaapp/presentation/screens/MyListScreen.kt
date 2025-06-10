package com.deepakjetpackcompose.mangaapp.presentation.screens

import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.presentation.component.ListTopBar
import com.deepakjetpackcompose.mangaapp.presentation.component.MangaPanel
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.AuthState
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MyListScreen(
    mangaViewModel: MangaViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val authState = mangaViewModel.authState.collectAsState()
    val myMangaList = mangaViewModel.myMangaList.collectAsState()
    val context= LocalContext.current
    val isLoading=mangaViewModel.isLoading.collectAsState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    LaunchedEffect(Unit) {
        if(authState.value== AuthState.Authenticated){
            mangaViewModel.getUserMangaList()
        }
    }


    Scaffold(topBar = {
        ListTopBar(
            title = "My List",
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 20.dp),
            onSearch = {navController.navigate(NavigationHelper.SearchScreen.route)},
        )
    }) { innerPadding ->
        if (authState.value == AuthState.UnAuthenticated) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1D1D1D))
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(30.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF313131))
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)) {

                        Text(
                            "My Profile",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Sign in to synchronize your manga",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(25.dp))
                        Button(
                            onClick = { navController.navigate(NavigationHelper.Login.route) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Continue")
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1D1D1D))
                    .padding(innerPadding)
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(30.dp))

                if (isLoading.value) {
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
                } else {
                    if (myMangaList.value.isEmpty()) {
                        Text(
                            text = "No manga in your list yet.",
                            color = Color.White,
                            fontSize = 16.sp
                        )

                    } else {
                        LazyColumn {
                            items(myMangaList.value) { manga ->

                                Row {
                                    MangaPanel(
                                        manga = manga,
                                        mangaViewModel = mangaViewModel,
                                        navController = navController
                                    )

                                    Spacer(Modifier.width(16.dp))
                                    Column {
                                        Text(
                                            manga.title,
                                            color = Color.White,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        Text(
                                            manga.description,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Normal,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        val encodedUrl = URLEncoder.encode(
                                            manga.imgUrl,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                        val encodedTitle = URLEncoder.encode(
                                            manga.title,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                        val encodedDescription = URLEncoder.encode(
                                            manga.description,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                        val encodedId = URLEncoder.encode(
                                            manga.id,
                                            StandardCharsets.UTF_8.toString()
                                        )

                                        Button(
                                            onClick = {
                                                mangaViewModel.removeManga(id = manga.id) { success, msg ->
                                                    if (success) {
                                                        Toast.makeText(
                                                            context,
                                                            msg,
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    } else Toast.makeText(
                                                        context,
                                                        msg,
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }

                                                mangaViewModel.getUserMangaList()

                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
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
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = null, tint = Color.White,
                                                    modifier = Modifier.size(25.dp)
                                                )
                                                Spacer(Modifier.width(10.dp))
                                                Text(
                                                    "Delete", color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(Modifier.height(20.dp))
                            }
                        }

                        Spacer(Modifier.height(20.dp))
                    }
                }

            }


        }
    }
}