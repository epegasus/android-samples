package com.sohaib.paging3.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sohaib.paging3.data.retrofit.entities.Quote
import com.sohaib.paging3.data.retrofit.api.ClientApi
import retrofit2.HttpException
import java.io.IOException

class QuotesPagingSource(private val clientApi: ClientApi) : PagingSource<Int, Quote>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Quote> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = clientApi.getQuotes(page)
            LoadResult.Page(
                data = response.results,
                prevKey = page.takeIf { it > FIRST_PAGE }?.dec(),
                nextKey = (page + 1).takeIf { page < response.totalPages },
            )
        } catch (e: IOException) {
            // Paging surfaces this via LoadState.Error (retry / UI).
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    // Page to request after invalidate (e.g. rotation); derived from last known anchor.
    override fun getRefreshKey(state: PagingState<Int, Quote>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}