package com.rasel.baseappcompose.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rasel.baseappcompose.data.model.UnsplashPhoto

class NewsPagingSource(
    private val newsApiService: NiaNetworkDataSource,
): PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        return try {
            val page = params.key ?: 1
            val response = newsApiService.searchPhotos()

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}