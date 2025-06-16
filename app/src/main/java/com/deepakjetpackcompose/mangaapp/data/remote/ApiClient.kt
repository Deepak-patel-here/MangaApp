package com.deepakjetpackcompose.mangaapp.data.remote

import com.deepakjetpackcompose.mangaapp.domain.model.ChapterModel
import com.deepakjetpackcompose.mangaapp.domain.model.Data
import com.deepakjetpackcompose.mangaapp.domain.model.Manga
import com.deepakjetpackcompose.mangaapp.domain.model.MangaDexResponse
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.deepakjetpackcompose.mangaapp.domain.model.chpater.Chapter
import com.deepakjetpackcompose.mangaapp.domain.model.chpater.ChapterData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.date
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


object ApiClient {
    val client: HttpClient= HttpClient{
        install(ContentNegotiation){
            json(json = Json { ignoreUnknownKeys=true
                isLenient = true
                coerceInputValues = true})
        }
    }

    suspend fun getAllMangas(): List<Manga>{
        val response: MangaDexResponse= client.get("https://api.mangadex.org/manga"){
            parameter("limit", 10)
            parameter("availableTranslatedLanguage[]", "en")
            contentType(ContentType.Application.Json)
        }.body()
        return response.data
    }



    suspend fun getMangaCover(coverId: String): String {
        val responseText = client.get("https://api.mangadex.org/cover/$coverId").bodyAsText()
        val jsonObj = Json.parseToJsonElement(responseText).jsonObject

        val data = jsonObj["data"]?.jsonObject ?: throw NullPointerException("Missing 'data'")
        val attributes = data["attributes"]?.jsonObject ?: throw NullPointerException("Missing 'attributes'")
        val fileName = attributes["fileName"]?.jsonPrimitive?.content ?: throw NullPointerException("Missing 'fileName'")

        return fileName
    }

    suspend fun getTopAiredManga(limit:Int=10):List<Manga>{
        val response: MangaDexResponse=client.get("https://api.mangadex.org/manga") {
            parameter("status[]", "ongoing")
            parameter("order[followedCount]", "desc")
            parameter("limit", limit)
        }.body()
        return response.data
    }

    suspend fun getFavouriteManga(limit:Int=10):List<Manga>{
        val response: MangaDexResponse=client.get("https://api.mangadex.org/manga") {
            parameter("order[followedCount]", "desc")
            parameter("limit", limit)
        }.body()
        return response.data
    }

    suspend fun getRecentlyUpdatedManga(limit:Int=10):List<Manga>{
        val response: MangaDexResponse=client.get("https://api.mangadex.org/manga") {
            parameter("order[latestUploadedChapter]", "desc")
            parameter("limit", limit)
        }.body()
        return response.data
    }

    suspend fun getNewlyReleasedManga(limit:Int=10):List<Manga>{
        val response: MangaDexResponse=client.get("https://api.mangadex.org/manga") {
            parameter("order[createdAt]", "desc")
            parameter("limit", limit)
        }.body()
        return response.data
    }

    suspend fun getChaptersForManga(mangaId: String): List<Data> {
        val response: ChapterModel= client.get("https://api.mangadex.org/chapter") {
            parameter("manga", mangaId)
            parameter("translatedLanguage[]", "en")
            parameter("order[chapter]", "asc")
            parameter("limit", 100)
        }.body()
        return response.data
    }


    suspend fun getChapterData(chapterId: String): ChapterData{
        val response: ChapterData=client.get("https://api.mangadex.org/at-home/server/$chapterId").body()
        return response
    }


    suspend fun searchManga(query: String): List<Manga> {
        val response = client.get("https://api.mangadex.org/manga") {
            parameter("title", query)
            parameter("limit", 20)
            parameter("includes[]", listOf("cover_art", "author")) // Optional
        }

        if (response.status.isSuccess()) {
            val result = response.body<MangaDexResponse>()  // Use your MangaResponse data class
            return result.data
        } else {
            throw Exception("Failed to search manga: ${response.status}")
        }
    }




}

fun main()= runBlocking {
    val mangas = ApiClient.getFavouriteManga(limit=10)
    val manga=mangas[0]
    println(manga.attributes.title)
    val chapters= ApiClient.getChaptersForManga(manga.id)
    chapters.forEach {chapter->
        println(chapter.attributes.title)
        println(chapter.id)
    }
    val chapter= ApiClient.getChapterData(chapters[0].id)
    println()
    println("${chapter.baseUrl}/data/${chapter.chapter?.hash}/${chapter.chapter?.dataSaver?.get(0)}")



}