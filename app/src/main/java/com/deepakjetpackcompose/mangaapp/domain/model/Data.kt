package com.deepakjetpackcompose.mangaapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val attributes: Attributes2,
    val id: String,
    val relationships: List<Relationship2>,
    val type: String
)