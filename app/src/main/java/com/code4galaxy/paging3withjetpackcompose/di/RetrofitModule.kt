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

/**
 * Dependency injection module for providing Retrofit-related instances and repositories.
 *
 * This module uses Dagger Hilt to inject dependencies throughout the application lifecycle.
 * It provides instances of [NewsService] for API communication and [NewsRepository] for data management.
 *
 * @see com.code4galaxy.paging3withjetpackcompose.data.NewsService
 * @see com.code4galaxy.paging3withjetpackcompose.data.repository.NewsRepository
 * @see dagger.hilt
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    /**
     * Provides a singleton instance of [NewsService] configured with Retrofit.
     *
     * @return [NewsService] instance for making network calls to fetch news data.
     *
     * This method:
     * - Configures Retrofit with the base URL defined in [ApiConstants.BASE_URL].
     * - Adds a Gson converter for parsing JSON responses.
     */
    @Singleton
    @Provides
    fun provideRetrofitInstance(): NewsService = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsService::class.java)

    /**
     * Provides a singleton instance of [NewsRepository] for managing data operations.
     *
     * @param newsApiService The [NewsService] instance used by the repository.
     * @return [NewsRepository] instance for accessing and managing news data.
     */
    @Singleton
    @Provides
    fun provideNewsRepository(newsApiService: NewsService): NewsRepository =
        NewsRepository(newsApiService)
}
