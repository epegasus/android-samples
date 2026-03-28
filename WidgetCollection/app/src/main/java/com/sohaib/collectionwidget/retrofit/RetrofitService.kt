package com.sohaib.collectionwidget.retrofit

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * @Author: SOHAIB AHMED
 * @Date: 17/09/2024.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://linkedin.com/in/epegasus
 */

data class ProductsResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val thumbnail: String
)

interface ProductsApiService {
    @GET("products")
    fun getProducts(): Call<ProductsResponse>
}

object RetrofitClient {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS) // Connection timeout
        .readTimeout(60, TimeUnit.SECONDS)    // Read timeout
        .writeTimeout(60, TimeUnit.SECONDS)   // Write timeout
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ProductsApiService = retrofit.create(ProductsApiService::class.java)
}