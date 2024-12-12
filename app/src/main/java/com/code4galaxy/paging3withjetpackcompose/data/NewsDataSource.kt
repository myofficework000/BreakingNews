/**
 * Implementation of [PagingSource] for paginating news articles fetched from the API.
 *
 * This class uses the Paging 3 library to load pages of news articles on demand, supporting infinite scrolling.
 *
 * @property newsApiService The Retrofit service to fetch news data.
 * @property query The search keyword(s) to filter news articles.
 * @property sortBy The sorting criteria for the articles. Common values include "publishedAt", "popularity", or "relevancy".
 *
 * @see com.code4galaxy.paging3withjetpackcompose.data.dto.Article
 * @see com.code4galaxy.paging3withjetpackcompose.data.NewsService
 */
class NewsDataSource(
    private val newsApiService: NewsService,
    private val query: String,
    private val sortBy: String
) : PagingSource<Int, Article>() {

    /**
     * Loads a page of news articles from the API.
     *
     * @param params Contains the requested load size and the key of the page to be loaded.
     * @return [LoadResult] containing the loaded data or an error.
     *
     * This method uses the [newsApiService] to fetch the data and constructs a [LoadResult.Page] with:
     * - `data`: The list of articles for the current page.
     * - `prevKey`: The key for the previous page (null if this is the first page).
     * - `nextKey`: The key for the next page (null if there are no more pages).
     */
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

    /**
     * Provides the key for the page that should be loaded after a refresh.
     *
     * @param state The current state of the paging system.
     * @return The key of the page to be loaded after refresh, or null if unavailable.
     *
     * This function uses the anchor position in the current list to find the closest page and calculates the appropriate key.
     */
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
