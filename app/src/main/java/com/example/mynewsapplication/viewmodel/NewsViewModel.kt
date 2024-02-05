package com.example.mynewsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynewsapplication.NewsModel

class NewsViewModel : ViewModel() {

    private var newsLiveData: MutableLiveData<List<NewsModel>>? = null

    //get news from API
    fun getNews(category: String?): MutableLiveData<List<NewsModel>>? {

        newsLiveData = category.let { NewsRepository().getNewsApiCall(it) }

        return newsLiveData
    }

    var newsData: LiveData<List<NewsModel>>? = null


}