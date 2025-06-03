package com.deepakjetpackcompose.mangaapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.mangaapp.domain.model.Manga
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.deepakjetpackcompose.mangaapp.domain.repository.MangaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MangaViewModel : ViewModel() {
    private val _repository = MangaRepository()
    private val _mangaList = MutableStateFlow<List<MangaUiModel>>(emptyList())
    val mangaList: StateFlow<List<MangaUiModel>> = _mangaList


    fun getAllManga() {
        viewModelScope.launch {
            try {
                val mangaList = _repository.getMangaList()
                Log.d("MANGA_VIEWMODEL", "Total mangas fetched: ${mangaList.size}")

                val uiModelList = mangaList.map { manga ->
                    val coverId = manga.relationships.firstOrNull { it.type == "cover_art" }?.id
                    val imageUrl = if (coverId != null) {
                        try {
                            val fileName = _repository.fetchMangaCover(coverId)
                            "https://uploads.mangadex.org/covers/${manga.id}/$fileName"
                        } catch (e: Exception) {
                            Log.e("MANGA_VIEWMODEL", "Cover fetch failed for ${manga.id}", e)
                            "https://via.placeholder.com/150"
                        }
                    } else {
                        Log.e("MANGA_VIEWMODEL", "No cover_art for ${manga.id}")
                        "https://via.placeholder.com/150"
                    }

                    val title = manga.attributes.title["en"] ?: "No Title"
                    val description = manga.attributes.description["en"] ?: "No Description"
                    val status = manga.attributes.status ?: "Unknown"
                    val year = manga.attributes.year ?: 0
                    val contentRating = manga.attributes.contentRating ?: "Unrated"
                    val id = manga.id
                    val isLocked = manga.attributes.isLocked ?: false

                    MangaUiModel(
                        title = title,
                        description = description,
                        status = status,
                        year = year,
                        contentRating = contentRating,
                        id = id,
                        isLocked = isLocked,
                        imgUrl = imageUrl
                    )
                }

                _mangaList.value = uiModelList
                Log.d("MANGA_VIEWMODEL", "UI Model list size: ${uiModelList.size}")

            } catch (e: Exception) {
                Log.e("MANGA_VIEWMODEL", "Error fetching mangas", e)
            }
        }
    }






}