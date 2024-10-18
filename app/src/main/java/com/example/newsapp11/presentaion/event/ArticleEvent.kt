package com.example.newsapp11.presentaion.event

import com.example.newsapp11.data.remote.model.Article

sealed interface ArticleEvent {
    data object UpdateBookmark : ArticleEvent
    data object UpdateIsLoading : ArticleEvent
//    data object OnBackClicked : ArticleEvent
//    data object OnShareClicked : ArticleEvent

    data class LoadArticle(val article: Article) : ArticleEvent
//    data class OnUrlClicked(val url: String) : ArticleEvent
}