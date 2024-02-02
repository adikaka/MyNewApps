package com.example.mynewsapplication.retrofit

data class NewsDataFromJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)