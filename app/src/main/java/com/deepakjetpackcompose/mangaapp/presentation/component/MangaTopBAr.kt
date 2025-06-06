package com.deepakjetpackcompose.mangaapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper

@Preview
@Composable
fun MangaTopBar(navController: NavController,modifier: Modifier = Modifier) {

    Row (modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,){


        Card( modifier = Modifier.size(35.dp),
            shape = CircleShape,
            elevation = CardDefaults.elevatedCardElevation(3.dp)
        ) {
            Image(painter = painterResource(R.drawable.manga_logo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }


        Spacer(Modifier.weight(1f))

        IconButton(onClick = {
            navController.navigate(NavigationHelper.SearchScreen.route)
        }) {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.bell_1),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
                )
        }
    }

}