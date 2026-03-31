package com.sohaib.composeretrofit.helpers.retrofit.models

data class Result(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)