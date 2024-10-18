package com.example.newsapp11.presentaion.state

import com.example.newsapp11.data.local.model.RoomArticle
import com.example.newsapp11.data.remote.model.Article

data class BookmarkState(
    val roomArticles: List<RoomArticle> = emptyList(),
    val articles: List<Article> = emptyList(),
    val loading: Boolean = false,
)