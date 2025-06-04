package com.deepakjetpackcompose.mangaapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Attributes2(
    val chapter: String,
    val createdAt: String,
    val externalUrl: String? = null,
    val isUnavailable: Boolean,
    val pages: Int,
    val publishAt: String,
    val readableAt: String,
    val title: String? = null, // ✅ Make this nullable
    val translatedLanguage: String,
    val updatedAt: String,
    val version: Int,
    val volume: String?=null
)
