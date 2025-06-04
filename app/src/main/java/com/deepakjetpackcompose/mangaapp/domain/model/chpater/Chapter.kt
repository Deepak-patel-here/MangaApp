package com.deepakjetpackcompose.mangaapp.domain.model.chpater

import kotlinx.serialization.Serializable


@Serializable
data class Chapter(
    val data: List<String>,
    val dataSaver: List<String>,
    val hash: String
)