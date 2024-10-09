package com.code4galaxy.paging3withjetpackcompose.data.dto

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)