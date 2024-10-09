package com.code4galaxy.paging3withjetpackcompose.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.code4galaxy.paging3withjetpackcompose.data.dto.Article

class NewsDataSource(
    private val newsApiService: NewsService,
    private val query: String,
    private val sortBy: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: ApiConstants.INITIAL_PAGE
            val response = newsApiService.getNews(query = query, page = page, sortBy = sortBy)

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

        override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}


