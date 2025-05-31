package com.deepakjetpackcompose.mangaapp.domain.repository

import com.deepakjetpackcompose.mangaapp.data.remote.ApiClient
import com.deepakjetpackcompose.mangaapp.domain.model.Manga

class MangaRepository {

    suspend fun getMangaList(): List<Manga>{
        return ApiClient.getAllMangas()
    }

    suspend fun fetchMangaCover(coverId: String): String{
        return ApiClient.getMangaCover(coverId = coverId)
    }
}