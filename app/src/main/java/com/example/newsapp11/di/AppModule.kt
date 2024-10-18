package com.example.newsapp11.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.newsapp11.NewsApplication
import com.example.newsapp11.data.local.converter.RoomConverter
import com.example.newsapp11.data.local.database.ArticleDao
import com.example.newsapp11.data.local.database.ArticleDatabase
import com.example.newsapp11.data.remote.ApiClient
import com.example.newsapp11.data.remote.ApiRoutes
import com.example.newsapp11.data.repositoryImpl.ArticleRepositoryImpl
import com.example.newsapp11.domain.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DataConversion
import io.ktor.http.ContentType
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): NewsApplication {
        return context as NewsApplication
    }

    @Singleton
    @Provides
    fun provideArticleDatabase(context: Application): ArticleDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = ArticleDatabase::class.java,
            name = "article_db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }

    @Singleton
    @Provides
    fun provideApiClient(): ApiClient {
        return ApiClient
    }

    @Singleton
    @Provides
    fun providesArticleRepository(articleDao: ArticleDao, client: ApiClient): ArticleRepository {
        return ArticleRepositoryImpl(articleDao, client)
    }

}