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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.data.remote.main
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.deepakjetpackcompose.mangaapp.presentation.component.MangaPanel
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.AuthState
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun SeeAllScreen(
    modifier: Modifier = Modifier,
    mangaViewModel: MangaViewModel,
    navController: NavHostController,
    title: String,
    code: Int
) {

    val topAired = mangaViewModel.getTopAired.collectAsState()
    val favorite = mangaViewModel.getFavourite.collectAsState()
    val newly = mangaViewModel.getNew.collectAsState()
    val updated = mangaViewModel.getUpdated.collectAsState()
    val systemController = rememberSystemUiController()
    val authState=mangaViewModel.authState.collectAsState()
    val context= LocalContext.current



    SideEffect {
        systemController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )

        systemController.setNavigationBarColor(
            color = Color(0xFF1D1D1D),
            darkIcons = false
        )
    }

    val mainList =
        rememberSaveable { mutableStateOf<List<MangaUiModel>>(emptyList<MangaUiModel>()) }
    when (code) {
        1 -> mainList.value = topAired.value
        2 -> mainList.value = favorite.value
        3 -> mainList.value = newly.value
        else -> mainList.value = updated.value
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D1D1D))
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center
    ) {

        Box() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(
                        painter = painterResource(R.drawable.left_arrow),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(20.dp))

                Text(
                    title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = {navController.navigate(NavigationHelper.SearchScreen.route)}) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }


            }
        }

        Spacer(Modifier.height(20.dp))
        LazyColumn {
            items(mainList.value) { manga ->

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

                        Button(
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
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null, tint = Color.White,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    "my list", color = Color.White
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }

        }
    }
}

