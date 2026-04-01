package com.sohaib.paging3.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sohaib.paging3.data.retrofit.entities.Quote
import com.sohaib.paging3.data.retrofit.api.ClientApi
import com.sohaib.paging3.data.paging.QuotesPagingSource
import kotlinx.coroutines.flow.Flow

class QuotesRepository(private val clientApi: ClientApi) {

    // Factory must return a new PagingSource each time so Paging3 can invalidate safely.
    fun quotesPagingFlow(): Flow<PagingData<Quote>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                // Network pages: no placeholder rows while loading.
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { QuotesPagingSource(clientApi) },
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}