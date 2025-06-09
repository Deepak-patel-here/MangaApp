package com.deepakjetpackcompose.mangaapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepakjetpackcompose.mangaapp.R


@Composable
fun ListTopBar(title:String,modifier: Modifier = Modifier) {

    Row(modifier= modifier.fillMaxWidth().background(Color(0xFF1D1D1D)).padding(vertical=5.dp),
        verticalAlignment = Alignment.CenterVertically){
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

        Spacer(Modifier.width(20.dp))
        Text(title, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.White
        )
    }

}