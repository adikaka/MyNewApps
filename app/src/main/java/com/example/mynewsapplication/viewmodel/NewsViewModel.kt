package com.example.mynewsapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynewsapplication.model.NewsModel
import com.example.mynewsapplication.data.repository.NewsRepository

class NewsViewModel : ViewModel() {

    private var newsLiveData: MutableLiveData<List<NewsModel>>? = null

    //get news from API
    fun getNews(category: String?): MutableLiveData<List<NewsModel>>? {

        newsLiveData = category.let { NewsRepository().getNewsApiCall(it) }

        return newsLiveData
    }

}