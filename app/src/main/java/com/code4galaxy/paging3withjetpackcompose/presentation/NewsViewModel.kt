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

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    fun getBreakingNews(query: String, sortBy: String): Flow<PagingData<Article>> =
        newsRepository.getNews(query, sortBy).cachedIn(viewModelScope)
}

