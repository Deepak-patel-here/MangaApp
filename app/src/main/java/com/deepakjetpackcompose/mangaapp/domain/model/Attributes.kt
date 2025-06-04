package com.deepakjetpackcompose.mangaapp.domain.model

data class Attributes(
    val chapter: String,
    val createdAt: String,
    val externalUrl: Any,
    val isUnavailable: Boolean,
    val pages: Int,
    val publishAt: String,
    val readableAt: String,
    val title: String,
    val translatedLanguage: String,
    val updatedAt: String,
    val version: Int,
    val volume: String
)