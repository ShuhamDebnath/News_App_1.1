package com.example.newsapp11.data.remote

object ApiRoutes {
    private const val BASE_URL = "https://newsapi.org"
    const val API_KEY = "d827e59294bc44a39441504c5dbad74d"
    const val GET_NEWS_EVERYTHING = "$BASE_URL/v2/everything"
    const val GET_NEWS_TOP_HEADLINES = "$BASE_URL/v2/top-headlines"
}