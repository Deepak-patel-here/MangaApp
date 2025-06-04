package com.deepakjetpackcompose.mangaapp.domain.model.chpater

import kotlinx.serialization.Serializable


@Serializable
data class Chapter(
    val data: List<String>?=emptyList(),
    val dataSaver: List<String>?=emptyList(),
    val hash: String?=null
)