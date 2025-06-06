package com.deepakjetpackcompose.mangaapp.presentation.component



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel

@Composable
fun ListOfNewManga(mangaViewModel: MangaViewModel,navController: NavController,newly:List<MangaUiModel>, modifier: Modifier = Modifier) {
    val title="New Releases"
    val code=3
    Column (modifier = modifier.fillMaxWidth()){
        Row (modifier = Modifier.fillMaxWidth().padding(end = 20.dp)) {
            Text(
                text = "Newly Released",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "see all",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Red,
                textAlign = TextAlign.Start,
                modifier = Modifier.clickable(onClick = {
                    mangaViewModel.getNewManga(20)
                    navController.navigate("${NavigationHelper.SeeAllScreen.route}/$title/$code")
                })
            )
        }
        Spacer(Modifier.height(10.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(newly) { item ->
                MangaPanel(mangaViewModel = mangaViewModel,navController=navController,manga = item)
                Spacer(Modifier.width(10.dp))
            }
        }
    }

}

//ListOfUpdatedManga