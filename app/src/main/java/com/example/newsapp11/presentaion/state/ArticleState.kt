package com.example.newsapp11.presentaion.state

import com.example.newsapp11.data.remote.model.Article

data class ArticleState(
    val article: Article? = null,
    val id: Int? = null,
    val isLoading: Boolean = false,
    val bookmark: Boolean = false,
)
