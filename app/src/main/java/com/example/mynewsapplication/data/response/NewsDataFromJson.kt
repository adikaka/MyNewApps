package com.example.mynewsapplication.data.response

data class NewsDataFromJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)