package com.sohaib.paging3.di

import com.sohaib.paging3.data.retrofit.RetrofitInstance
import com.sohaib.paging3.data.retrofit.api.ClientApi
import com.sohaib.paging3.data.repository.QuotesRepository
import com.sohaib.paging3.presentation.viewModels.QuotesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object KoinModules {

    private val appModule = module {
        single<ClientApi> { RetrofitInstance.clientApi }
        single { QuotesRepository(clientApi = get()) }
        viewModel { QuotesViewModel(quotesRepository = get()) }
    }

    val modules = listOf(appModule)
}