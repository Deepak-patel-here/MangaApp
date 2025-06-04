package com.deepakjetpackcompose.mangaapp.domain.model.chpater

import kotlinx.serialization.Serializable

@Serializable
data class ChapterData(
    val baseUrl: String?=null,
    val chapter: Chapter?=null,
    val result: String?=null
)