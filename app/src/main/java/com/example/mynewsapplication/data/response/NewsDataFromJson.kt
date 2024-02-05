package com.example.mynewsapplication.data.response

import com.example.mynewsapplication.data.response.Article

data class NewsDataFromJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)