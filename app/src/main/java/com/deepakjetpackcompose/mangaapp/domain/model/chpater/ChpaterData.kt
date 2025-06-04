package com.deepakjetpackcompose.mangaapp.domain.model.chpater

import kotlinx.serialization.Serializable

@Serializable
data class ChapterData(
    val baseUrl: String,
    val chapter: Chapter,
    val result: String
)