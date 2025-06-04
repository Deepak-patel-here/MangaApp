package com.deepakjetpackcompose.mangaapp.domain.model.responseconverter

data class ChapterResponse(
    val id:String,
    val volume:String?=null,
    val chapter:String?=null,
    val pages:Int
)
