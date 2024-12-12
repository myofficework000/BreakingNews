package com.code4galaxy.paging3withjetpackcompose.data

import com.code4galaxy.paging3withjetpackcompose.data.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface definition for the Retrofit API service used to fetch news articles.
 *
 * This interface uses the Retrofit HTTP client to communicate with a news API endpoint. It defines a single 
 * method [getNews] for retrieving paginated and filtered news data based on the provided parameters.
 *
 * @see com.code4galaxy.paging3withjetpackcompose.data.dto.NewsResponse
 */
interface NewsService {

    /**
     * Fetches news articles from the API based on the query, page number, and sorting criteria.
     *
     * @param query The search keyword(s) to filter news articles. (e.g., "technology", "health")
     * @param page The page number for pagination, starting from 1.
     * @param sortBy The sorting criteria for the articles. Common values include "publishedAt", "popularity", or "relevancy".
     * @return [NewsResponse] containing a list of news articles and metadata about the response.
     *
     * Example usage:
     * ```kotlin
     * val newsResponse = newsService.getNews(
     *     query = "android",
     *     page = 1,
     *     sortBy = "publishedAt"
     * )
     * ```
     *
     * Notes:
     * - Ensure that the `ApiConstants.API_KEY` is set correctly in your project.
     * - `ApiConstants.PAGE_SIZE` determines the number of items per page.
     * - This method is a `suspend` function and must be called within a coroutine or another suspend function.
     */
    @GET("everything?apiKey=${ApiConstants.API_KEY}&pageSize=${ApiConstants.PAGE_SIZE}")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("sortBy") sortBy: String
    ): NewsResponse
}
