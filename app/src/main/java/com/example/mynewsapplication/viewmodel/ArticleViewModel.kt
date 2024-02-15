package com.example.mynewsapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mynewsapplication.data.repository.NewsRepository
import com.example.mynewsapplication.model.NewsModel

class ArticleViewModel(private val repository: NewsRepository) : ViewModel() {
    fun getNewsWithSource(newsList: MutableList<NewsModel>, source: String): MutableList<NewsModel> {
        return repository.getNewsWithSource(newsList, source)
    }

    fun getNewsWithSearch(newsList: MutableList<NewsModel>, keyword: String): MutableList<NewsModel> {
        return repository.getNewsWithSearch(newsList, keyword)
    }
}