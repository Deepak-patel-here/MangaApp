package com.deepakjetpackcompose.mangaapp.domain.repository

import com.deepakjetpackcompose.mangaapp.data.remote.ApiClient
import com.deepakjetpackcompose.mangaapp.domain.model.Data
import com.deepakjetpackcompose.mangaapp.domain.model.Manga
import com.deepakjetpackcompose.mangaapp.domain.model.chpater.ChapterData

class MangaRepository {

    suspend fun getMangaList(): List<Manga>{
        return ApiClient.getAllMangas()
    }

    suspend fun getTopAiredManga(limit:Int): List<Manga>{
        return ApiClient.getTopAiredManga(limit=limit)
    }

    suspend fun getFavouriteManga(limit:Int): List<Manga>{
        return ApiClient.getFavouriteManga(limit = limit)
    }

    suspend fun getUpdatedManga(limit:Int): List<Manga>{
        return ApiClient.getRecentlyUpdatedManga(limit = limit)
    }

    suspend fun getNewManga(limit:Int): List<Manga>{
        return ApiClient.getNewlyReleasedManga(limit = limit)
    }

    suspend fun getAllChapter(mangaId:String): List<Data>{
        return ApiClient.getChaptersForManga(mangaId = mangaId)
    }

    suspend fun fetchChapterPanel(chapterId:String): ChapterData{
        return ApiClient.getChapterData(chapterId=chapterId)

    }

    suspend fun fetchMangaCover(coverId: String): String{
        return ApiClient.getMangaCover(coverId = coverId)
    }

    suspend fun searchManga(query: String): List<Manga>{
        return ApiClient.searchManga(query=query)
    }
}