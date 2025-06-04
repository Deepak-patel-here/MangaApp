package com.deepakjetpackcompose.mangaapp.domain.repository

import com.deepakjetpackcompose.mangaapp.data.remote.ApiClient
import com.deepakjetpackcompose.mangaapp.domain.model.Manga

class MangaRepository {

    suspend fun getMangaList(): List<Manga>{
        return ApiClient.getAllMangas()
    }

    suspend fun getTopAiredManga(): List<Manga>{
        return ApiClient.getTopAiredManga()
    }

    suspend fun getFavouriteManga(): List<Manga>{
        return ApiClient.getFavouriteManga()
    }

    suspend fun getUpdatedManga(): List<Manga>{
        return ApiClient.getRecentlyUpdatedManga()
    }

    suspend fun getNewManga(): List<Manga>{
        return ApiClient.getNewlyReleasedManga()
    }

    suspend fun fetchMangaCover(coverId: String): String{
        return ApiClient.getMangaCover(coverId = coverId)
    }
}