package com.example.mynewsapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class NewsModel(
    val headLine: String,
    val image: String?,
    val description: String?,
    val url: String?,
    val source: String?,
    val time: String?,
    val content: String?

) : Parcelable