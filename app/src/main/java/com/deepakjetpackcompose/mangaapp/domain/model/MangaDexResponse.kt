package com.deepakjetpackcompose.mangaapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MangaDexResponse(
    val result: String,
    val response: String,
    val data: List<Manga>
)

@Serializable
data class Manga(
    val id: String,
    val type: String,
    val attributes: Attributes,
    val relationships: List<Relationship> = emptyList()
)

@Serializable
data class Attributes(
    val title: Map<String, String>,
    val altTitles: List<Map<String, String>> = emptyList(),
    val description: Map<String, String> = emptyMap(),
    val isLocked: Boolean,
    val links: Links = Links(),
    val originalLanguage: String? = null,
    val lastVolume: String? = "",
    val lastChapter: String? = "",
    val publicationDemographic: String? = null,
    val status: String,
    val year: Int? = null,
    val contentRating: String? = null,
    val tags: List<Tag> = emptyList(),
    val state: String,
    val chapterNumbersResetOnNewVolume: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val version: Int,
    val availableTranslatedLanguages: List<String> = emptyList(),
    val latestUploadedChapter: String? = null
)

@Serializable
data class Links(
    val al: String? = null,
    val ap: String? = null,
    val bw: String? = null,
    val mu: String? = null,
    val nu: String? = null,
    val amz: String? = null,
    val ebj: String? = null,
    val mal: String? = null,
    val raw: String? = null
)

@Serializable
data class Tag(
    val id: String,
    val type: String,
    val attributes: TagAttributes,
    val relationships: List<Relationship> = emptyList()
)

@Serializable
data class TagAttributes(
    val name: Map<String, String>,
    val description: Map<String, String> = emptyMap(),
    val group: String,
    val version: Int
)

@Serializable
data class Relationship(
    val id: String,
    val type: String
)