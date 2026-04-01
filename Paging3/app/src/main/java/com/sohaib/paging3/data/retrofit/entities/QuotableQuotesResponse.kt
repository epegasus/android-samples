package com.sohaib.paging3.data.retrofit.entities

data class QuotableQuotesResponse(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Quote>,
    val totalCount: Int,
    val totalPages: Int,
)
