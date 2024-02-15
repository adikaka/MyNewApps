package com.example.mynewsapplication.data.service

import com.example.mynewsapplication.data.response.NewsDataFromJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/top-headlines")
    fun getNews(@Query("country") country : String, @Query("category") category : String?, @Query("apiKey") key : String) : Call<NewsDataFromJson>

}