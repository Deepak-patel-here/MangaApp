package com.deepakjetpackcompose.mangaapp.domain.model

data class ChapterModel(
    val `data`: List<Data>,
    val limit: Int,
    val offset: Int,
    val response: String,
    val result: String,
    val total: Int
)