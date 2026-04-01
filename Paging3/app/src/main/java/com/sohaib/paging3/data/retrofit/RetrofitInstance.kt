package com.sohaib.paging3.data.retrofit

import com.sohaib.paging3.data.retrofit.api.ClientApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://quotable.io/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val clientApi: ClientApi by lazy { retrofit.create(ClientApi::class.java) }
}