package com.sohaib.retrofit.demo.interfaces

import com.sohaib.retrofit.demo.models.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ClientApi {

    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("/posts")
    fun getAllPostsCall(): Call<List<Post>>

}