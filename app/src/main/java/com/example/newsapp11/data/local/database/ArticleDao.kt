package com.example.newsapp11.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsapp11.data.local.model.RoomArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Upsert
    suspend fun upsertArticle(roomArticle: RoomArticle)

    @Delete
    suspend fun deleteArticle(roomArticle: RoomArticle)

    @Query("SELECT * FROM roomarticle WHERE IsFavourite = 1")
    fun getAllBookmarkArticle(): Flow<List<RoomArticle>>

    @Query("SELECT * FROM roomarticle WHERE IsFavourite = 0")
    fun getAllArticle(): Flow<List<RoomArticle>>

    @Query("SELECT EXISTS(SELECT 1 FROM roomarticle WHERE title = :title)")
    suspend fun searchTitle(title: String): Boolean

//    @Query("SELECT id FROM roomarticle WHERE title = :title LIMIT 1")
    @Query("SELECT id FROM roomarticle WHERE title = :title AND isFavourite = 1")
    suspend fun searchTitleAndGetId(title: String): Int?

}