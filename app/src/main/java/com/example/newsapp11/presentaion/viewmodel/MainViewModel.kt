package com.example.newsapp11.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp11.data.local.model.RoomArticle
import com.example.newsapp11.data.model.Response
import com.example.newsapp11.data.remote.model.Article
import com.example.newsapp11.data.remote.model.NewsResp
import com.example.newsapp11.domain.repository.ArticleRepository
import com.example.newsapp11.presentaion.event.ArticleEvent
import com.example.newsapp11.presentaion.event.BookmarkEvent
import com.example.newsapp11.presentaion.event.HomeEvent
import com.example.newsapp11.presentaion.event.SearchEvent
import com.example.newsapp11.presentaion.state.ArticleState
import com.example.newsapp11.presentaion.state.BookmarkState
import com.example.newsapp11.presentaion.state.HomeState
import com.example.newsapp11.presentaion.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ArticleRepository) : ViewModel() {

    private var databaseOperation: DatabaseOperation

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    private val _bookmarkState = MutableStateFlow(BookmarkState())
    val bookmarkState = _bookmarkState.asStateFlow()

    private val _articleState = MutableStateFlow(ArticleState())
    val articleState = _articleState.asStateFlow()


    init {
        Log.d("TAG", ": init")
        databaseOperation = DatabaseOperation(repository)
        onHomeEvent(HomeEvent.LoadArticles)
        onBookmarkEvent(BookmarkEvent.LoadArticles)
    }

    fun onHomeEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadArticles -> {
                viewModelScope.launch(Dispatchers.IO) {
                    Log.d("TAG", "onHomeEvent: test viewModelScope.launch")
                    onHomeEvent(HomeEvent.UpdateIsLoading)

                    databaseOperation.getTopHeadLines(
                        country = "us",
                        page = _homeState.value.page.toString()
                    ) { response ->
//                        Log.d("TAG", "onHomeEvent: response")
                        when (response) {
                            is Response.Error -> {
                                Log.d("TAG", "Response.Error : ${response.message}")
                                onHomeEvent(HomeEvent.UpdateIsLoading)
                            }

                            Response.Loading -> {
                                onHomeEvent(HomeEvent.UpdateIsLoading)
                            }

                            is Response.Success -> {
                                val mutableList = _homeState.value.articles.toMutableList()
                                mutableList.addAll(response.data.articles)
                                _homeState.update {
                                    it.copy(
                                        articles = mutableList.toList()
                                    )
                                }
                                onHomeEvent(HomeEvent.UpdateIsLoading)
                                Log.d("TAG", "onHomeEvent: Articles = ${_homeState.value.articles.size} ")
                            }
                        }
                    }
                }
            }


            is HomeEvent.OnArticleClicked -> {
                Log.d("TAG", "onHomeEvent: ")
                onArticleEvent(ArticleEvent.LoadArticle(event.article))
            }

            is HomeEvent.OnCategoryClicked -> {
                Log.d("TAG", "onHomeEvent: HomeEvent.OnCategoryClicked category ${event.category}")
                if (_homeState.value.category == event.category) {
                    _homeState.update {
                        it.copy(
                            category = null,
                            articles = emptyList(),
                            page = 1,
                        )
                    }
                    onHomeEvent(HomeEvent.LoadArticles)
                } else {
                    _homeState.update {
                        it.copy(
                            category = event.category,
                            articles = emptyList(),
                            page = 1
                        )
                    }
                    viewModelScope.launch {
                        onHomeEvent(HomeEvent.UpdateIsLoading)
                        databaseOperation.getTopHeadLines(
                            category = event.category.name,
                            page = _homeState.value.page.toString()
                        ) { response ->
                            when (response) {
                                is Response.Error -> {
                                    Log.d("TAG", "Response.Error : ${response.message}")
                                    onHomeEvent(HomeEvent.UpdateIsLoading)
//                                    _homeState.update {
//                                        it.copy(
//                                            loading = false
//                                        )
//                                    }
                                }

                                Response.Loading -> {
                                    onHomeEvent(HomeEvent.UpdateIsLoading)
                                }

                                is Response.Success -> {
//                                    Log.d("TAG", "onHomeEvent: response ${response.data.articles}")
                                    val mutableList = _homeState.value.articles.toMutableList()
                                    mutableList.addAll(response.data.articles)
                                    _homeState.update {
                                        it.copy(
                                            articles = mutableList.toList()
                                        )
                                    }
//                                    _homeState.update {
//                                        it.copy(
//                                            loading = false
//                                        )
//                                    }
                                    onHomeEvent(HomeEvent.UpdateIsLoading)
                                }
                            }
                        }
                    }
                }
            }

            is HomeEvent.OnCountryClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    onHomeEvent(HomeEvent.UpdateIsLoading)
                    databaseOperation.getTopHeadLines(country = event.country.code) { response ->
                        when (response) {
                            is Response.Error -> {
                                Log.d("TAG", "Response.Error : ${response.message}")
                            }

                            Response.Loading -> {

                            }

                            is Response.Success -> {
                                _homeState.update {
                                    it.copy(
                                        articles = response.data.articles
                                    )
                                }
                            }
                        }
                    }
                    onHomeEvent(HomeEvent.UpdateIsLoading)
                }
            }

            is HomeEvent.OnLanguageClicked -> {
                viewModelScope.launch {
                    onHomeEvent(HomeEvent.UpdateIsLoading)
                    databaseOperation.getTopHeadLines(language = event.language.code) { response ->
                        when (response) {
                            is Response.Error -> {
                                Log.d("TAG", "Response.Error : ${response.message}")
                            }

                            Response.Loading -> {

                            }

                            is Response.Success -> {
                                _homeState.update {
                                    it.copy(
                                        articles = response.data.articles
                                    )
                                }
                            }
                        }
                    }
                    onHomeEvent(HomeEvent.UpdateIsLoading)
                }
            }

            HomeEvent.UpdateIsLoading -> {
                Log.d("TAG", "onHomeEvent: HomeEvent.UpdateIsLoading called ")
                _homeState.update {
                    it.copy(
                        loading = !it.loading
                    )
                }
                Log.d(
                    "TAG", "onHomeEvent: HomeEvent.UpdateIsLoading ${_homeState.value.loading} "
                )
            }

            HomeEvent.UpdateMoreVert -> {
                _homeState.update {
                    it.copy(moreVert = !it.moreVert)
                }
            }

            HomeEvent.UpdateShowCountry -> {
                _homeState.update {
                    it.copy(showCountry = !it.showCountry)
                }
            }

            HomeEvent.UpdateShowLanguage -> {
                _homeState.update {
                    it.copy(showLanguage = !it.showLanguage)
                }
            }

            HomeEvent.IncreasePage -> {
                _homeState.update {
                    it.copy(page = it.page + 1)
                }
            }
        }

    }

    fun onArticleEvent(event: ArticleEvent) {
        when (event) {
            is ArticleEvent.LoadArticle -> {
                Log.d("TAG", "onArticleEvent: ${event.article}")
                _articleState.update {
                    it.copy(
                        article = event.article
                    )
                }
                viewModelScope.launch {
                    _articleState.value.article?.title?.let { title ->
                        val id = databaseOperation.searchTitleAndGetId(title)
                        if (id != null) {
                            _articleState.update {
                                it.copy(
                                    bookmark = true,
                                    id = id
                                )
                            }
                        } else {
                            _articleState.update {
                                it.copy(
                                    bookmark = false,
                                    id = null
                                )
                            }
                        }
                    }
                }
            }

            ArticleEvent.UpdateBookmark -> {
                Log.d("TAG", "onArticleEvent: called ")
                if (_articleState.value.bookmark) {
                    viewModelScope.launch {

                        _articleState.update {
                            it.copy(
                                bookmark = false
                            )
                        }

                        if (_articleState.value.article != null && _articleState.value.id != null) {
                            databaseOperation.deleteFromDatabase(
                                article = _articleState.value.article!!,
                                id = _articleState.value.id!!,
                                isFavourite = _articleState.value.bookmark,
                                onComplete = {
                                    //todo : show toast article removed
                                }
                            )
                        }
                        Log.d("TAG", "onArticleEvent: deleted ")
                    }

                } else {
                    viewModelScope.launch {
                        _articleState.update {
                            it.copy(
                                bookmark = true
                            )
                        }
                        _articleState.value.article?.let {
                            databaseOperation.upsertToDatabase(
                                article = it,
                                isFavourite = _articleState.value.bookmark,
                                onComplete = {
                                    //todo : show toast article removed
                                }
                            )
                        }
                        Log.d("TAG", "onArticleEvent: created ")

                    }
                }
            }

            ArticleEvent.UpdateIsLoading -> {
                _articleState.update {
                    it.copy(
                        isLoading = !it.isLoading
                    )
                }
            }
        }
    }

    fun onBookmarkEvent(event: BookmarkEvent) {
        when (event) {
            BookmarkEvent.LoadArticles -> {
                viewModelScope.launch {

                    Log.d("TAG", " test 2")
                    _bookmarkState.update {
                        it.copy(
                            loading = true
                        )
                    }
//                    onBookmarkEvent(BookmarkEvent.UpdateLoading)
                    Log.d("TAG", " test 3 ${_bookmarkState.value.loading}")

                    databaseOperation.getAllBookmarkArticle { roomArticles ->
                        Log.d("TAG", " test 4")
                        val articles: ArrayList<Article> = arrayListOf()
                        roomArticles.forEach { roomArticle ->
                            val article = databaseOperation.roomArticleToArticle(
                                roomArticle,
                                isBookmark = {},
                                id = {}
                            )
                            articles.add(0, article)
                        }

                        _bookmarkState.update {
                            it.copy(
                                articles = articles.toList()
                            )
                        }
                        Log.d("TAG", "onBookmarkEvent: articles $articles")

                        Log.d("TAG", " test 5")
                        _bookmarkState.update {
                            it.copy(
                                loading = false
                            )
                        }
//                        onBookmarkEvent(BookmarkEvent.UpdateLoading)
                        Log.d("TAG", " test 6 ${_bookmarkState.value.loading}")
                    }
                }


            }

            is BookmarkEvent.OnArticleClicked -> {
                onArticleEvent(ArticleEvent.LoadArticle(event.article))
            }

            BookmarkEvent.UpdateLoading -> {
                _bookmarkState.update {
                    it.copy(
                        loading = !it.loading
                    )
                }
            }
        }
    }

    fun onSearchEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnArticleClicked -> {
                onArticleEvent(ArticleEvent.LoadArticle(event.article))
            }

            SearchEvent.OnSearch -> {
                viewModelScope.launch {
                    onSearchEvent(SearchEvent.UpdateLoading)

                    databaseOperation.getEverything(
                        q = _searchState.value.search,
                        page = _searchState.value.page.toString()
                    ) { response ->
                        when (response) {
                            is Response.Error -> {
                                Log.d("TAG", "onSearchEvent: error : ${response.message}")
                                onSearchEvent(SearchEvent.UpdateLoading)
                            }

                            Response.Loading -> {
                                onSearchEvent(SearchEvent.UpdateLoading)
                            }

                            is Response.Success -> {

                                val mutableList = _searchState.value.articles.toMutableList()
                                mutableList.addAll(response.data.articles)

                                _searchState.update {
                                    it.copy(
                                        articles = mutableList
                                    )
                                }
                                onSearchEvent(SearchEvent.UpdateLoading)
                            }
                        }
                    }
                }
            }

            SearchEvent.UpdateLoading -> {
                _searchState.update {
                    it.copy(
                        loading = !it.loading
                    )
                }
            }

            is SearchEvent.UpdateSearch -> {
                _searchState.update {
                    it.copy(
                        search = event.article
                    )
                }
            }

            SearchEvent.IncreasePage -> {
                _searchState.update {
                    it.copy(
                        page = it.page + 1
                    )
                }
            }
        }

    }
}

class DatabaseOperation(
    private val repository: ArticleRepository,
) {

    suspend fun upsertToDatabase(article: Article, isFavourite: Boolean, onComplete: () -> Unit) {
        val roomArticle = articleToRoomArticle(article, isFavourite)
        repository.upsertArticle(roomArticle).also {
            onComplete()
        }
    }

    suspend fun deleteFromDatabase(
        article: Article,
        id: Int,
        isFavourite: Boolean,
        onComplete: () -> Unit
    ) {
        val roomArticle = articleToRoomArticle(article, isFavourite, id = id)
        repository.deleteArticle(roomArticle).also {
            onComplete()
        }
    }

    suspend fun getAllBookmarkArticle(onComplete: (List<RoomArticle>) -> Unit) {
        repository.getAllBookmarkArticle().collect { roomArticles ->
            onComplete(roomArticles)
        }
    }

    suspend fun getAllArticle(onComplete: (List<RoomArticle>) -> Unit) {
        repository.getAllArticle().collect { roomArticles ->
            onComplete(roomArticles)
        }
    }

    suspend fun searchTitleAndGetId(title: String): Int? {
        return repository.searchTitleAndGetId(title)
    }

    fun articleToRoomArticle(
        article: Article,
        isFavourite: Boolean,
        id: Int = 0,
    ): RoomArticle {
        return RoomArticle(
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            source = article.source,
            title = article.title,
            url = article.title,
            urlToImage = article.urlToImage,
            //extra
            isFavourite = isFavourite,
            id = id
        )
    }

    fun roomArticleToArticle(
        roomArticle: RoomArticle,
        isBookmark: (Boolean) -> Unit,
        id: (Int) -> Unit,
    ): Article {
        isBookmark(roomArticle.isFavourite)
        id(roomArticle.id)
        return Article(
            author = roomArticle.author,
            content = roomArticle.content,
            description = roomArticle.description,
            publishedAt = roomArticle.publishedAt,
            source = roomArticle.source,
            title = roomArticle.title,
            url = roomArticle.title,
            urlToImage = roomArticle.urlToImage,
        )
    }

    suspend fun getTopHeadLines(
        country: String? = null,
        category: String? = null,
        source: String? = null,
        q: String? = null,
        language: String? = null,
        from: String? = null,
        to: String? = null,
        sortBy: String? = null,
        pageSize: String? = null,
        page: String? = null,
        onResponse: (Response<NewsResp>) -> Unit
    ) {
        Log.d("TAG", "getTopHeadLines: ")
        repository.getTopHeadLines(
            country,
            category,
            source,
            q,
            language,
            from,
            to,
            sortBy,
            pageSize,
            page,
        ).also { response ->
            onResponse(response)

        }
    }

    suspend fun getEverything(
        country: String? = null,
        category: String? = null,
        source: String? = null,
        q: String? = null,
        language: String? = null,
        from: String? = null,
        to: String? = null,
        sortBy: String? = null,
        pageSize: String? = null,
        page: String? = null,
        onResponse: (Response<NewsResp>) -> Unit
    ) {
        Log.d("TAG", "getEverything: ")
        repository.getEverything(
            country,
            category,
            source,
            q,
            language,
            from,
            to,
            sortBy,
            pageSize,
            page,
        ).also { response ->
            onResponse(response)

        }
    }
}