package com.code4galaxy.paging3withjetpackcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.code4galaxy.paging3withjetpackcompose.data.dto.Article
import com.code4galaxy.paging3withjetpackcompose.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * ViewModel for managing news-related data and UI logic.
 *
 * This class interacts with the [NewsRepository] to fetch and cache paginated news data.
 *
 * @property newsRepository The repository instance for accessing news data.
 *
 * @see com.code4galaxy.paging3withjetpackcompose.data.repository.NewsRepository
 * @see androidx.lifecycle.ViewModel
 * @see androidx.paging.cachedIn
 */
@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    /**
     * Retrieves paginated breaking news articles.
     *
     * @param query The search keyword(s) to filter news articles.
     * @param sortBy The sorting criteria for the articles (e.g., "publishedAt", "popularity").
     * @return A [Flow] emitting [PagingData] of [Article] objects, representing the paginated data stream.
     *
     * This method caches the fetched data within the ViewModel scope using [cachedIn].
     *
     * @see com.code4galaxy.paging3withjetpackcompose.data.dto.Article
     * @see androidx.paging.PagingData
     */
    fun getBreakingNews(query: String, sortBy: String): Flow<PagingData<Article>> =
        newsRepository.getNews(query, sortBy).cachedIn(viewModelScope)
}

