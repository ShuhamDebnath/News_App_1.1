package com.example.newsapp11.presentaion.event

import com.example.newsapp11.data.remote.model.Article
import com.example.newsapp11.presentaion.utils.Category
import com.example.newsapp11.presentaion.utils.Country
import com.example.newsapp11.presentaion.utils.Language

sealed interface HomeEvent {
    data object LoadArticles : HomeEvent
    data object UpdateMoreVert : HomeEvent
    data object UpdateShowCountry : HomeEvent
    data object UpdateShowLanguage : HomeEvent
    data object UpdateIsLoading : HomeEvent
    data object IncreasePage : HomeEvent

    data class OnArticleClicked(val article: Article) : HomeEvent
    data class OnCategoryClicked(val category: Category) : HomeEvent
    data class OnCountryClicked(val country :Country) : HomeEvent
    data class OnLanguageClicked(val language: Language) : HomeEvent
}