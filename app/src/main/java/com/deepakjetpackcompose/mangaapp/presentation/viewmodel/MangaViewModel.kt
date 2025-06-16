package com.deepakjetpackcompose.mangaapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.mangaapp.domain.model.ChapterModel
import com.deepakjetpackcompose.mangaapp.domain.model.Manga
import com.deepakjetpackcompose.mangaapp.domain.model.MangaUiModel
import com.deepakjetpackcompose.mangaapp.domain.model.User
import com.deepakjetpackcompose.mangaapp.domain.model.chpater.Chapter
import com.deepakjetpackcompose.mangaapp.domain.model.chpater.ChapterData
import com.deepakjetpackcompose.mangaapp.domain.model.responseconverter.ChapterResponse
import com.deepakjetpackcompose.mangaapp.domain.repository.MangaRepository
import com.deepakjetpackcompose.mangaapp.presentation.constants.MANGA
import com.deepakjetpackcompose.mangaapp.presentation.constants.USER
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MangaViewModel : ViewModel() {
    private val _repository = MangaRepository()
    private val _mangaList = MutableStateFlow<List<MangaUiModel>>(emptyList())
    val mangaList: StateFlow<List<MangaUiModel>> = _mangaList.asStateFlow()

    private val _getTopAired= MutableStateFlow<List<MangaUiModel>>(emptyList())
    val getTopAired: StateFlow<List<MangaUiModel>> = _getTopAired.asStateFlow()

    private val _getFavourite= MutableStateFlow<List<MangaUiModel>>(emptyList())
    val getFavourite: StateFlow<List<MangaUiModel>> = _getFavourite.asStateFlow()

    private val _getUpdated= MutableStateFlow<List<MangaUiModel>>(emptyList())
    val getUpdated: StateFlow<List<MangaUiModel>> = _getUpdated.asStateFlow()

    private val _getNew= MutableStateFlow<List<MangaUiModel>>(emptyList())
    val getNew: StateFlow<List<MangaUiModel>> = _getNew.asStateFlow()

    private val _getChapters= MutableStateFlow<List<ChapterResponse>>(emptyList())
    val getChapters: StateFlow<List<ChapterResponse>> = _getChapters.asStateFlow()

    private val _chapterImages = MutableStateFlow<ChapterData>(ChapterData())
    val chapterImages: StateFlow<ChapterData> = _chapterImages.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _chapterError = MutableStateFlow<String?>(null)
    val chapterError: StateFlow<String?> = _chapterError.asStateFlow()

    private val _searchMangaList = MutableStateFlow<List<MangaUiModel>>(emptyList())
    val searchMangaList: StateFlow<List<MangaUiModel>> = _searchMangaList.asStateFlow()

    private val auth: FirebaseAuth= Firebase.auth
    private val firestore: FirebaseFirestore= FirebaseFirestore.getInstance()

    private val _authState= MutableStateFlow<AuthState>(AuthState.UnAuthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _myMangaLIst= MutableStateFlow<List<MangaUiModel>>(emptyList())
    val myMangaList: StateFlow<List<MangaUiModel>> = _myMangaLIst.asStateFlow()

    private val _myUser= MutableStateFlow<User>(User("",""))
    val myUser: StateFlow<User> = _myUser

    init {
        checkUser()
    }

    fun checkUser(){
        if(auth.currentUser!=null){
            _authState.value= AuthState.Authenticated
        }else _authState.value= AuthState.UnAuthenticated
    }




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

    fun getTopAiredManga(limit:Int=10){
        viewModelScope.launch {
            try {
                val mangaLists = _repository.getTopAiredManga(limit=limit)
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

    fun getFavouriteManga(limit:Int=10){
        viewModelScope.launch {
            try {
                val mangaLists = _repository.getFavouriteManga(limit=limit)
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

    fun getUpdatedManga(limit:Int=10){
        viewModelScope.launch {
            try {
                val mangaLists = _repository.getUpdatedManga(limit=limit)
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

    fun getNewManga(limit:Int=10){
        viewModelScope.launch {
            try {
                val mangaLists = _repository.getNewManga(limit = limit)
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
        _searchMangaList.value=emptyList()
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

    fun clearSearchList(){
        _searchMangaList.value=emptyList()
    }


    fun signUp(name:String,email:String,password:String,onResult:(Boolean,String)->Unit){
        _authState.value= AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                _authState.value= AuthState.Authenticated
                val uid=it.user?.uid ?: return@addOnSuccessListener
                val user=User(name=name,email=email)
                firestore.collection(USER).document(uid).set(user)
                    .addOnSuccessListener {
                        _authState.value = AuthState.Authenticated
                        onResult(true, "SignUp Successful")
                    }
                    .addOnFailureListener {
                        _authState.value = AuthState.UnAuthenticated
                        onResult(false, "Auth OK but Firestore failed: ${it.localizedMessage}")
                    }
            }
            .addOnFailureListener {
                _authState.value= AuthState.UnAuthenticated
                onResult(false,it.localizedMessage)
            }
    }

    fun login(email:String,password:String,onResult:(Boolean,String)->Unit){
        _authState.value= AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                _authState.value= AuthState.Authenticated
                onResult(true,"Login Successful")
            }
            .addOnFailureListener {
                _authState.value= AuthState.UnAuthenticated
                onResult(false,it.localizedMessage)
            }
    }


    fun addManga(manga: MangaUiModel,onResult: (Boolean, String) -> Unit){
        val uid=auth.currentUser?.uid
        if(uid==null){
            onResult(false, "User not authenticated")
            return
        }

        firestore.collection(USER).document(uid).collection(MANGA).document(manga.id).set(manga)
            .addOnSuccessListener {
                onResult(true, "Manga added to your list")
            }
            .addOnFailureListener {
                onResult(false, it.localizedMessage ?: "Unknown error")
            }
    }

    fun getUserMangaList() {
        _myMangaLIst.value=emptyList()
        _isLoading.value=true
        val uid = auth.currentUser?.uid
        if(uid!=null) {
            firestore.collection(USER)
                .document(uid)
                .collection(MANGA)
                .get()
                .addOnSuccessListener { snapshot ->
                    val list = snapshot.toObjects(MangaUiModel::class.java)
                    _myMangaLIst.value = list
                    _isLoading.value = false

                }
                .addOnFailureListener {
                    _myMangaLIst.value = emptyList()
                    _isLoading.value = false

                }
        }
    }

    fun removeManga(id:String,onResult: (Boolean, String) -> Unit){
        val uid=auth.currentUser?.uid
        if(uid==null){
            onResult(false, "User not authenticated")
            return
        }

        firestore.collection(USER).document(uid).collection(MANGA).document(id).delete()
            .addOnSuccessListener {
                onResult(true, "Manga deleted successfully")
            }
            .addOnFailureListener {
                onResult(false, it.localizedMessage ?: "Unknown error")
            }
    }

    fun getUser(){
        val uid=auth.currentUser?.uid
        if(uid!=null){
            firestore.collection(USER).document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("name")
                        val email = document.getString("email")
                        // Access other fields similarly
                        Log.d("PROFILE", "Name: $name, Email: $email")

                        _myUser.value= User(name = name?:"unknown", email = email?:"unknown")
                    } else {
                        Log.d("PROFILE", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("PROFILE", "get failed with ", exception)
                }
        }
    }

    fun signOut(){
        auth.signOut()
        _authState.value= AuthState.UnAuthenticated
    }









}


sealed class AuthState(){
    object UnAuthenticated: AuthState()
    object Authenticated: AuthState()
    object Loading: AuthState()
}