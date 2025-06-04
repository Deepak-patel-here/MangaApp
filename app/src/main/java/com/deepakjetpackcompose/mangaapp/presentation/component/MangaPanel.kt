package com.deepakjetpackcompose.mangaapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import io.ktor.util.Encoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MangaPanel(mangaViewModel: MangaViewModel,navController: NavController,manga: MangaUiModel) {
    val encodedUrl = URLEncoder.encode(manga.imgUrl, StandardCharsets.UTF_8.toString())
    val encodedTitle = URLEncoder.encode(manga.title, StandardCharsets.UTF_8.toString())
    val encodedDescription = URLEncoder.encode(manga.description, StandardCharsets.UTF_8.toString())
    val encodedId = URLEncoder.encode(manga.id, StandardCharsets.UTF_8.toString())
    Card(
        modifier = Modifier
            .height(170.dp)
            .width(130.dp)
            .clickable(onClick = {
                mangaViewModel.getMangaChapter(mangaId = manga.id)
                navController.navigate(
                    "${NavigationHelper.ChapterScreen.route}/$encodedUrl/$encodedTitle/$encodedDescription/$encodedId"
                )
            }),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val painter = rememberAsyncImagePainter(model = manga.imgUrl)

            AsyncImage(
                model = manga.imgUrl,
                contentDescription = manga.title,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }

}