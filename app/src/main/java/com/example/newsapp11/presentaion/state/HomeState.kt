package com.example.newsapp11.presentaion.state

import com.example.newsapp11.data.remote.model.Article
import com.example.newsapp11.presentaion.utils.Category
import com.example.newsapp11.presentaion.utils.Country
import com.example.newsapp11.presentaion.utils.Language

data class HomeState(
    val articles: List<Article> = emptyList(),
    val category: Category? = null,
    val country: Country? = null,
    val showCountry: Boolean = false,
    val language: Language? = null,
    val showLanguage: Boolean = false,
    val moreVert: Boolean = false,
    val loading :Boolean = false,
    val page :Int = 1
)
