package com.example.newsapp11.presentaion.event

import com.example.newsapp11.data.remote.model.Article

sealed interface BookmarkEvent {

    data object LoadArticles :BookmarkEvent
    data object UpdateLoading :BookmarkEvent

    data class OnArticleClicked(val article: Article) :BookmarkEvent

}