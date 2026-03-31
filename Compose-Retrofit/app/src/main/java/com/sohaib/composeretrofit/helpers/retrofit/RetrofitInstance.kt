package com.sohaib.composeretrofit.helpers.retrofit

import com.sohaib.composeretrofit.helpers.retrofit.interfaces.ClientApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://dummyjson.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val clientApi: ClientApi by lazy {
        retrofit.create(ClientApi::class.java)
    }
}