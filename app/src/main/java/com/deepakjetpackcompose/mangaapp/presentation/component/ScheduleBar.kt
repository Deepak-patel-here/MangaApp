package com.deepakjetpackcompose.mangaapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.deepakjetpackcompose.mangaapp.domain.model.ScheduleDate
import java.time.LocalDate

@Composable
fun ScheduleBar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val days = remember {
        (0 until 30).map {
            val date = today.plusDays(it.toLong())
            ScheduleDate(date, isToday = date == today, isSelected = date == selectedDate)
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(days) { scheduleDate ->
            val backgroundColor = when {
                scheduleDate.isToday -> Color.Red
                scheduleDate.isSelected -> Color.DarkGray
                else -> Color.Gray
            }

            val textColor = Color.White

            Column(
                modifier = Modifier
                    .border(width = 2.dp, color = backgroundColor, shape = RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .clickable { onDateSelected(scheduleDate.date) }
                    .padding(12.dp)
                    ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = scheduleDate.date.dayOfWeek.name.take(3),
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
                Text(
                    text = scheduleDate.date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
            }
        }
    }
}
