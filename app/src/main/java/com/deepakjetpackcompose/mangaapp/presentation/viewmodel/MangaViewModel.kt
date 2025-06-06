package com.deepakjetpackcompose.mangaapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.mangaapp.domain.model.ChapterModel
import com.deepakjetpackcompose.mangaapp.domain.model.Manga
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.deepakjetpackcompose.mangaapp.domain.model.chpater.Chapter
import com.deepakjetpackcompose.mangaapp.domain.model.chpater.ChapterData
import com.deepakjetpackcompose.mangaapp.domain.model.responseconverter.ChapterResponse
import com.deepakjetpackcompose.mangaapp.domain.repository.MangaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MangaViewModel : ViewModel() {
    private val _repository = MangaRepository()
    private val _mangaList = MutableStateFlow<List<MangaUiModel>>(emptyList())
    val mangaList: StateFlow<List<MangaUiModel>> = _mangaList

    private val _getTopAired= MutableStateFlow<List<MangaUiModel>>(emptyList())
    val getTopAired: StateFlow<List<MangaUiModel>> = _getTopAired

    private val _getFavourite= MutableStateFlow<List<MangaUiModel>>(emptyList())
    val getFavourite: StateFlow<List<MangaUiModel>> = _getFavourite

    private val _getUpdated= MutableStateFlow<List<MangaUiModel>>(emptyList())
    val getUpdated: StateFlow<List<MangaUiModel>> = _getUpdated

    private val _getNew= MutableStateFlow<List<MangaUiModel>>(emptyList())
    val getNew: StateFlow<List<MangaUiModel>> = _getNew

    private val _getChapters= MutableStateFlow<List<ChapterResponse>>(emptyList())
    val getChapters: StateFlow<List<ChapterResponse>> = _getChapters

    private val _chapterImages = MutableStateFlow<ChapterData>(ChapterData())
    val chapterImages: StateFlow<ChapterData> = _chapterImages

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _chapterError = MutableStateFlow<String?>(null)
    val chapterError: StateFlow<String?> = _chapterError

    private val _searchMangaList = MutableStateFlow<List<MangaUiModel>>(emptyList())
    val searchMangaList: StateFlow<List<MangaUiModel>> = _searchMangaList


    fun getAllManga() {
        _isLoading.value=true
        viewModelScope.launch {
            try {
                val mangaLists = _repository.getMangaList()
                Log.d("MANGA_VIEWMODEL", "Total mangas fetched: ${mangaLists.size}")
                val uiModelList = mangaLists.map { manga ->
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
            }finally {
                _isLoading.value=false
            }
        }
    }

    fun getTopAiredManga(){
        viewModelScope.launch {
            try {
                val mangaLists = _repository.getTopAiredManga()
                Log.d("MANGA_VIEWMODEL", "Total mangas fetched: ${mangaLists.size}")

                val uiModelList = mangaLists.map { manga ->
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

                _getTopAired.value = uiModelList
                Log.d("MANGA_VIEWMODEL", "UI Model list size: ${uiModelList.size}")

            } catch (e: Exception) {
                Log.e("MANGA_VIEWMODEL", "Error fetching mangas", e)
            }
        }
    }

    fun getFavouriteManga(){
        viewModelScope.launch {
            try {
                val mangaLists = _repository.getFavouriteManga()
                Log.d("MANGA_VIEWMODEL", "Total mangas fetched: ${mangaLists.size}")

                val uiModelList = mangaLists.map { manga ->
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

                _getFavourite.value = uiModelList
                Log.d("MANGA_VIEWMODEL", "UI Model list size: ${uiModelList.size}")

            } catch (e: Exception) {
                Log.e("MANGA_VIEWMODEL", "Error fetching mangas", e)
            }
        }
    }

    fun getUpdatedManga(){
        viewModelScope.launch {
            try {
                val mangaLists = _repository.getUpdatedManga()
                Log.d("MANGA_VIEWMODEL", "Total mangas fetched: ${mangaLists.size}")

                val uiModelList = mangaLists.map { manga ->
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

                _getUpdated.value = uiModelList
                Log.d("MANGA_VIEWMODEL", "UI Model list size: ${uiModelList.size}")

            } catch (e: Exception) {
                Log.e("MANGA_VIEWMODEL", "Error fetching mangas", e)
            }
        }
    }

    fun getNewManga(){
        viewModelScope.launch {
            try {
                val mangaLists = _repository.getNewManga()
                Log.d("MANGA_VIEWMODEL", "Total mangas fetched: ${mangaLists.size}")

                val uiModelList = mangaLists.map { manga ->
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

                _getNew.value = uiModelList
                Log.d("MANGA_VIEWMODEL", "UI Model list size: ${uiModelList.size}")

            } catch (e: Exception) {
                Log.e("MANGA_VIEWMODEL", "Error fetching mangas", e)
            }
        }
    }


    fun getMangaChapter(mangaId:String){
        _isLoading.value=true
        _getChapters.value = emptyList()
        _chapterError.value=null
        viewModelScope.launch {
            try {
                val chapterList=_repository.getAllChapter(mangaId = mangaId)
                if (chapterList.isEmpty()) {
                    _chapterError.value = "Chapters unavailable"
                }
                val chapterModelList=chapterList.map { ch->
                    val id=ch.id
                    val chapter = ch.attributes.chapter ?: "Unknown"
                    val volume = ch.attributes.volume ?: "N/A"
                    val pages = ch.attributes.pages ?: 0

                    ChapterResponse(
                        id=id,
                        chapter = chapter,
                        volume = volume,
                        pages=pages
                    )
                }

                _getChapters.value=chapterModelList

            } catch (e: Exception) {
                Log.e("MANGA_VIEWMODEL", "Error fetching chapters", e)
                _chapterError.value = "Failed to load chapters"
            }finally {
                _isLoading.value=false
            }
        }
    }

    fun getChapterImages(chapterId:String){
        _chapterImages.value= ChapterData()
        viewModelScope.launch {
            try {
                _chapterImages.value=_repository.fetchChapterPanel(chapterId=chapterId)
            }catch (e: Exception){
                Log.e("MANGA_VIEWMODEL", "Error fetching chapters", e)

            }
        }
    }

    fun searchManga(query:String){
        _isLoading.value=true
        viewModelScope.launch {
            try {
                val searchList= _repository.searchManga(query=query)
                val mangaModel=searchList.map { manga ->
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

                _searchMangaList.value=mangaModel
            }catch (e: Exception){
                _searchMangaList.value = emptyList()
            }finally {
                _isLoading.value=false
            }
        }
    }






}