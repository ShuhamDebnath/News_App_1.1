package com.example.newsapp11.domain.repository

import com.example.newsapp11.data.local.model.RoomArticle
import com.example.newsapp11.data.model.Response
import com.example.newsapp11.data.remote.model.NewsResp
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    //local
    suspend fun upsertArticle(roomArticle: RoomArticle)
    suspend fun deleteArticle(roomArticle: RoomArticle)
    suspend fun getAllBookmarkArticle(): Flow<List<RoomArticle>>
    suspend fun getAllArticle(): Flow<List<RoomArticle>>
    suspend fun searchTitleAndGetId(title: String): Int?

    //remote

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
    ): Response<NewsResp>

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
    ): Response<NewsResp>
}