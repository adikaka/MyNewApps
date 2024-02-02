package com.example.mynewsapplication.architecture

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mynewsapplication.BuildConfig
import com.example.mynewsapplication.MainActivity
import com.example.mynewsapplication.NewsModel
import com.example.mynewsapplication.retrofit.NewsDataFromJson
import com.example.mynewsapplication.retrofit.RetrofitHelper
import com.example.mynewsapplication.retrofit.NewsApi
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    // get news from API
    fun getNewsApiCall(category: String?): MutableLiveData<List<NewsModel>> {

        val newsList = MutableLiveData<List<NewsModel>>()

        val call = RetrofitHelper.getInstance().create(NewsApi::class.java)
            .getNews("in", category, BuildConfig.API_KEY) //put your api key here

        call.enqueue(object : Callback<NewsDataFromJson> {
            override fun onResponse(
                call: Call<NewsDataFromJson>,
                response: Response<NewsDataFromJson>
            ) {

                if (response.isSuccessful) {

                    val body = response.body()

                    if (body != null) {
                        val tempNewsList = mutableListOf<NewsModel>()

                        body.articles.forEach {
                            tempNewsList.add(
                                NewsModel(
                                    it.title,
                                    it.urlToImage,
                                    it.description,
                                    it.url,
                                    it.source.name,
                                    it.publishedAt,
                                    it.content
                                )
                            )
                        }
                        newsList.value = tempNewsList
                    }

                } else {

                    val jsonObj: JSONObject?

                    try {
                        jsonObj = response.errorBody()?.string()?.let { JSONObject(it) }
                        if (jsonObj != null) {
                            MainActivity.apiRequestError = true
                            MainActivity.errorMessage = jsonObj.getString("message")
                            val tempNewsList = mutableListOf<NewsModel>()
                            newsList.value = tempNewsList
                        }
                    } catch (e: JSONException) {
                        Log.d("JSONException", "" + e.message)
                    }

                }
            }

            override fun onFailure(call: Call<NewsDataFromJson>, t: Throwable) {

                MainActivity.apiRequestError = true
                MainActivity.errorMessage = t.localizedMessage as String
                Log.d("err_msg", "msg" + t.localizedMessage)
            }
        })

        return newsList
    }

}