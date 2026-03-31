package com.sohaib.composeretrofit.helpers.retrofit.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.sohaib.composeretrofit.helpers.pagination.ArticlesPagingSource
import com.sohaib.composeretrofit.helpers.retrofit.RetrofitInstance

class ArticlesViewModel : ViewModel() {

    private val pagingSource = ArticlesPagingSource(RetrofitInstance.clientApi)

    val pagingData = Pager(config = PagingConfig(pageSize = 100), pagingSourceFactory = { pagingSource }).flow.cachedIn(viewModelScope)
}