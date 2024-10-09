package com.code4galaxy.paging3withjetpackcompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.code4galaxy.paging3withjetpackcompose.data.ApiConstants
import com.code4galaxy.paging3withjetpackcompose.data.NewsDataSource
import com.code4galaxy.paging3withjetpackcompose.data.NewsService
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val newsService: NewsService
) {
    fun getNews(query: String, sortBy: String) = Pager(
        config = PagingConfig(
            pageSize = ApiConstants.PAGE_SIZE,
        ),
        pagingSourceFactory = {
            NewsDataSource(newsService, query, sortBy)
        }
    ).flow
}

