package com.example.newsapp11.data.local.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp11.data.remote.model.Source
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class RoomArticle(
    @SerialName("author")
    val author: String?,
    @SerialName("content")
    val content: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("publishedAt")
    val publishedAt: String?,
    @SerialName("source")
    val source: Source,
    @SerialName("title")
    val title: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("urlToImage")
    val urlToImage: String?,
    //extra
    @SerialName("isLiked")
    val isFavourite: Boolean,
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Int = 0,
)