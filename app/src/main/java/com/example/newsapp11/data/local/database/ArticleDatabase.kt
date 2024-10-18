package com.example.newsapp11.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp11.data.local.converter.RoomConverter
import com.example.newsapp11.data.local.model.RoomArticle

@TypeConverters(RoomConverter::class)
@Database(entities = [RoomArticle::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}