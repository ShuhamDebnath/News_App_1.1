package com.example.newsapp11.data.repositoryImpl

import android.util.Log
import com.example.newsapp11.data.local.database.ArticleDao
import com.example.newsapp11.data.local.model.RoomArticle
import com.example.newsapp11.data.model.Response
import com.example.newsapp11.data.remote.ApiClient
import com.example.newsapp11.data.remote.ApiRoutes
import com.example.newsapp11.data.remote.model.NewsResp
import com.example.newsapp11.domain.repository.ArticleRepository
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val dao: ArticleDao, private val client: ApiClient) :
    ArticleRepository {
    override suspend fun upsertArticle(roomArticle: RoomArticle) {
        dao.upsertArticle(roomArticle)
    }

    override suspend fun deleteArticle(roomArticle: RoomArticle) {
        dao.deleteArticle(roomArticle)
    }

    override suspend fun getAllBookmarkArticle(): Flow<List<RoomArticle>> {
        return dao.getAllBookmarkArticle()
    }

    override suspend fun getAllArticle(): Flow<List<RoomArticle>> {
        return dao.getAllArticle()
    }

    override suspend fun searchTitleAndGetId(title: String): Int? {
        return dao.searchTitleAndGetId(title)
    }

    override suspend fun getEverything(
        country: String?,
        category: String?,
        source: String?,
        q: String?,
        language: String?,
        from: String?,
        to: String?,
        sortBy: String?,
        pageSize: String?,
        page: String? ,
    ): Response<NewsResp> {
        return try {
            val response = client.ktorHttpClient.get(ApiRoutes.GET_NEWS_EVERYTHING) {
                contentType(ContentType.Application.Json)
                parameter("apiKey", ApiRoutes.API_KEY)
                if (country != null) {
                    parameter("country", country)
                }
                if (category != null) {
                    parameter("category", category)
                }
                if (source != null) {
                    parameter("source", source)
                }
                if (q != null) {
                    parameter("q", q)
                }
                if (language != null) {
                    parameter("language", language)
                }
                if (from != null) {
                    parameter("from", from)
                }
                if (to != null) {
                    parameter("to", to)
                }
                if (sortBy != null) {
                    parameter("sortBy", sortBy)
                }
                if (pageSize != null) {
                    parameter("pageSize", pageSize)
                }
                if (page != null) {
                    parameter("page", page)
                }
            }.body<NewsResp>()

            Log.d("TAG", "getEverything: response : $response ")

            Response.Success(response)
        } catch (e: Exception) {
            Log.d("TAG", "getEverything: error $e")
            Response.Error(e.localizedMessage!!)
        }
    }

    override suspend fun getTopHeadLines(
        country: String?,
        category: String?,
        source: String?,
        q: String?,
        language: String?,
        from: String?,
        to: String?,
        sortBy: String?,
        pageSize: String?,
        page: String?
    ): Response<NewsResp> {
        return try {
            val response = client.ktorHttpClient.get(ApiRoutes.GET_NEWS_TOP_HEADLINES) {
                contentType(ContentType.Application.Json)
                parameter("apiKey", ApiRoutes.API_KEY)
                if (country != null) {
                    parameter("country", country)
                }
                if (category != null) {
                    parameter("category", category)
                }
                if (source != null) {
                    parameter("source", source)
                }
                if (q != null) {
                    parameter("q", q)
                }
                if (language != null) {
                    parameter("language", language)
                }
                if (from != null) {
                    parameter("from", from)
                }
                if (to != null) {
                    parameter("to", to)
                }
                if (sortBy != null) {
                    parameter("sortBy", sortBy)
                }
                if (pageSize != null) {
                    parameter("pageSize", pageSize)
                }
                if (page != null) {
                    parameter("page", page)
                }
            }.body<NewsResp>()

            Log.d("TAG", "getTopHeadLines: response : $response ")

            Response.Success(response)
        } catch (e: Exception) {
            Log.d("TAG", "getTopHeadLines: error $e")
            Response.Error(e.localizedMessage!!)
        }
    }
}