package com.deepakjetpackcompose.mangaapp.domain.model

data class MangaUiModel(
    val title:String="",
    val description:String="",
    val status:String="",
    val id:String="",
    val isLocked: Boolean=false,
    val year:Int=0,
    val contentRating:String="",
    val imgUrl:String=""
)
