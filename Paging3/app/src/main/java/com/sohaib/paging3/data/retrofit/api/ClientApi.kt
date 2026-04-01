package com.sohaib.paging3.data.retrofit.api

import com.sohaib.paging3.data.retrofit.entities.QuotableQuotesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientApi {

    @GET("quotes")
    suspend fun getQuotes(@Query("page") page: Int): QuotableQuotesResponse
}