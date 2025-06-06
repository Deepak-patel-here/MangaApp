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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.presentation.component.MangaPanel
import com.deepakjetpackcompose.mangaapp.presentation.component.SearchMangaPanel
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SearchScreen(navController: NavController,mangaViewModel: MangaViewModel,modifier: Modifier = Modifier) {
    val systemController = rememberSystemUiController()
    val query = remember { mutableStateOf("") }
    val keyboard= LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val isLoading=mangaViewModel.isLoading.collectAsState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    val searchList=mangaViewModel.searchMangaList.collectAsState()
    LaunchedEffect(Unit) {
        mangaViewModel.clearSearchList()
        focusRequester.requestFocus()
        keyboard?.show()
    }

    SideEffect {
        systemController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )

        systemController.setNavigationBarColor(
            color = Color.Black,
            darkIcons = false
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D1D1D))
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.fillMaxWidth()){
            Row (verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)){
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(
                        painter = painterResource(R.drawable.left_arrow),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                }
                Spacer(Modifier.width(20.dp))

                OutlinedTextField(
                    value = query.value,
                    onValueChange = {query.value=it},
                    modifier = Modifier.focusRequester(focusRequester),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    placeholder = { Text("search") },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Red,
                        unfocusedContainerColor = Color(0xFF393939),
                        focusedContainerColor = Color(0xFF393939),
                        unfocusedPlaceholderColor = Color.LightGray,
                        focusedTextColor = Color.White
                    ),
                    maxLines = 1,
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            mangaViewModel.searchManga(query = query.value)
                            keyboard?.hide()
                        }
                    )

                )
            }
        }

        Spacer(Modifier.height(20.dp))

        if(isLoading.value){
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
        }else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(searchList.value) { manga ->
                    SearchMangaPanel(
                        mangaViewModel = mangaViewModel,
                        manga = manga,
                        navController = navController
                    )
                }

            }
        }
    }
}