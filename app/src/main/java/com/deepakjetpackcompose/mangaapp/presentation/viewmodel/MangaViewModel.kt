package com.deepakjetpackcompose.mangaapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.mangaapp.domain.model.Manga
import com.deepakjetpackcompose.mangaapp.domain.repository.MangaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MangaViewModel: ViewModel() {
    private val _repository= MangaRepository()
    private val _mangaList= MutableStateFlow<List<Manga>>(emptyList())
    val mangaList: StateFlow<List<Manga>> = _mangaList

    private val _fileName= MutableStateFlow<String>("")
    val fileName: StateFlow<String> = _fileName

    fun getAllManga(){
        viewModelScope.launch {
            _mangaList.value= _repository.getMangaList()
        }
    }

    fun getFileName(coverId: String){
        viewModelScope.launch {
            _fileName.value = _repository.fetchMangaCover(coverId = coverId)
        }
    }





}