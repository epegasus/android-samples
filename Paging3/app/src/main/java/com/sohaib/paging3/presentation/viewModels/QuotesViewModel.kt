package com.sohaib.paging3.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sohaib.paging3.data.retrofit.entities.Quote
import com.sohaib.paging3.data.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow

class QuotesViewModel(quotesRepository: QuotesRepository) : ViewModel() {

    val quotes: Flow<PagingData<Quote>> = quotesRepository
        .quotesPagingFlow()
        .cachedIn(viewModelScope)
}