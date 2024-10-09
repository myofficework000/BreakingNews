package com.code4galaxy.paging3withjetpackcompose.data

import com.code4galaxy.paging3withjetpackcompose.data.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("everything?apiKey=${ApiConstants.API_KEY}&pageSize=${ApiConstants.PAGE_SIZE}")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("sortBy") sortBy: String
    ): NewsResponse
}
