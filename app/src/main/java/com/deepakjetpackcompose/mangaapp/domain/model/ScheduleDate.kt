package com.deepakjetpackcompose.mangaapp.domain.model

import java.time.LocalDate

data class ScheduleDate(
    val date: LocalDate,
    val isToday: Boolean = false,
    val isSelected: Boolean = false
)