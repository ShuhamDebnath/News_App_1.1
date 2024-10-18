package com.example.newsapp11.presentaion.state

import com.example.newsapp11.data.remote.model.Article

data class SearchState(
    val articles: List<Article> = emptyList(),
    val loading: Boolean = false,
    val article: Article? = null,
    val search: String = "",
    val page: Int = 1,
)
