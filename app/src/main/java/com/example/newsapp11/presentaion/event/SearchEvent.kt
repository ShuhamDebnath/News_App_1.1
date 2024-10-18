package com.example.newsapp11.presentaion.event

import com.example.newsapp11.data.remote.model.Article

sealed interface SearchEvent {
    data object OnSearch : SearchEvent
    data object UpdateLoading : SearchEvent
    data object IncreasePage : SearchEvent
    data class OnArticleClicked(val article: Article) : SearchEvent
    data class UpdateSearch(val article: String) : SearchEvent


}