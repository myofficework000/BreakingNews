package com.code4galaxy.paging3withjetpackcompose.di

import com.code4galaxy.paging3withjetpackcompose.data.ApiConstants
import com.code4galaxy.paging3withjetpackcompose.data.NewsService
import com.code4galaxy.paging3withjetpackcompose.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): NewsService = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsService::class.java)

    @Singleton
    @Provides
    fun provideNewsRepository(newsApiService: NewsService): NewsRepository =
        NewsRepository(newsApiService)
}