package com.example.mynewsapplication.retrofit

data class Source(
    val id: Any,
    val name: String,
    val sameArticles: MutableList<Article>
)