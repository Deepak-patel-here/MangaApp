package com.deepakjetpackcompose.mangaapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepakjetpackcompose.mangaapp.domain.model.responseconverter.ChapterResponse

@Composable
fun ChaptersComponent(getChapters:List<ChapterResponse>,modifier: Modifier = Modifier) {
    LazyColumn {
        items(getChapters) { item ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = {}),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "chapter : ${item.chapter ?: 0}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
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