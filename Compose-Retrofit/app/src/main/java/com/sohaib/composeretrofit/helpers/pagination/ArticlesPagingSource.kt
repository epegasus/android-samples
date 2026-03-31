package com.sohaib.composeretrofit.helpers.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sohaib.composeretrofit.helpers.retrofit.interfaces.ClientApi
import com.sohaib.composeretrofit.helpers.retrofit.models.Product
import com.sohaib.composeretrofit.helpers.utils.HelperUtils.TAG

class ArticlesPagingSource(private val api: ClientApi) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {

        val nextPage = params.key ?: 1
        val skip = (nextPage - 1) * 10
        val response = api.getData(skip)
        val productList = response.body()?.products ?: emptyList()

        Log.d(TAG, "load: Skip: $skip, NextPage: $nextPage")
        return LoadResult.Page(
            data = productList,
            prevKey = if (nextPage == 1) null else nextPage.minus(1),
            nextKey = if (productList.isEmpty()) null else nextPage.plus(1),
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        Log.d(TAG, "getRefreshKey: called")
        val temp = state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
        Log.d(TAG, "getRefreshKey: $temp")
        return temp
    }
}