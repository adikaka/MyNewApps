package com.example.mynewsapplication.data.response

import com.example.mynewsapplication.data.response.Article

data class Source(
    val id: Any,
    val name: String,
    val sameArticles: MutableList<Article>
)