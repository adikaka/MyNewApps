package com.example.mynewsapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mynewsapplication.data.repository.NewsRepository
import com.example.mynewsapplication.model.NewsModel

class SourceViewModel(private val repository: NewsRepository): ViewModel() {
    fun getUniqueSource(newsList: MutableList<NewsModel>): MutableList<String>{
        return repository.getUniqueSource(newsList)
    }
}