package com.deepakjetpackcompose.mangaapp.data.remote

import com.deepakjetpackcompose.mangaapp.domain.model.Manga
import com.deepakjetpackcompose.mangaapp.domain.model.MangaDexResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object ApiClient {
    val client: HttpClient= HttpClient{
        install(ContentNegotiation){
            json(json = Json { ignoreUnknownKeys=true })
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

    private fun parameter(string: String, i: Int) {}

    suspend fun getMangaCover(coverId: String): String {
        val responseText = client.get("https://api.mangadex.org/cover/$coverId").bodyAsText()
        val jsonObj = Json.parseToJsonElement(responseText).jsonObject

        val data = jsonObj["data"]?.jsonObject ?: throw NullPointerException("Missing 'data'")
        val attributes = data["attributes"]?.jsonObject ?: throw NullPointerException("Missing 'attributes'")
        val fileName = attributes["fileName"]?.jsonPrimitive?.content ?: throw NullPointerException("Missing 'fileName'")

        return fileName
    }
}