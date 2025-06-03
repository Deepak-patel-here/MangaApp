package com.deepakjetpackcompose.mangaapp.presentation.screens

import android.util.Log
import androidx.collection.CircularArray
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel

@Composable
fun TestingScreen(mangaViewModel: MangaViewModel,modifier: Modifier = Modifier) {
    val mangaList =mangaViewModel.mangaList.collectAsState()
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .memoryCache { MemoryCache.Builder(context).maxSizePercent(0.25).build() }
        .crossfade(true)
        .logger(DebugLogger())
        .build()



    LaunchedEffect(Unit) {
        mangaViewModel.getAllManga()
    }

    if(mangaList.value.isNotEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(mangaList.value) { manga ->
                Row(modifier = Modifier.fillMaxWidth().padding(20.dp)) {

                    LaunchedEffect(manga.imgUrl) {
                        Log.d("MANGA_IMAGE", "Image URL: ${manga.imgUrl}")
                    }
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = manga.imgUrl,
                            imageLoader = imageLoader
                        ),
                        contentDescription = manga.title,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(manga.title)
                }
            }
        }
    }else{
        CircularProgressIndicator()
    }


}