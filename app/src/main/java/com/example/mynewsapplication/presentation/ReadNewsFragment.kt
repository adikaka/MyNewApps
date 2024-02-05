package com.example.mynewsapplication.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.mynewsapplication.NewsModel
import com.example.mynewsapplication.base.BaseFragment
import com.example.mynewsapplication.databinding.FragmentReadNewsBinding
import com.example.mynewsapplication.utils.Constants.NEWS_CONTENT
import com.example.mynewsapplication.utils.Constants.NEWS_DESCRIPTION
import com.example.mynewsapplication.utils.Constants.NEWS_IMAGE_URL
import com.example.mynewsapplication.utils.Constants.NEWS_PUBLICATION_TIME
import com.example.mynewsapplication.utils.Constants.NEWS_SOURCE
import com.example.mynewsapplication.utils.Constants.NEWS_TITLE
import com.example.mynewsapplication.utils.Constants.NEWS_URL

class ReadNewsFragment : BaseFragment<FragmentReadNewsBinding>() {

    private lateinit var newsData: ArrayList<NewsModel>

    @SuppressLint("SetJavaScriptEnabled")
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReadNewsBinding {
        return FragmentReadNewsBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        val newsWebView = binding.newswebview

        // Retrieve data from arguments instead of intent
        val newsUrl = arguments?.getString(NEWS_URL)
        val newsContent = arguments?.getString(NEWS_CONTENT) + ". get paid version to hear full news."

        // Create NewsModel instance
        newsData = ArrayList(1)
        newsData.add(
            NewsModel(
                arguments?.getString(NEWS_TITLE) ?: "",
                arguments?.getString(NEWS_IMAGE_URL),
                arguments?.getString(NEWS_DESCRIPTION),
                newsUrl ?: "",
                arguments?.getString(NEWS_SOURCE),
                arguments?.getString(NEWS_PUBLICATION_TIME),
                newsContent
            )
        )

        // Webview configuration
        newsWebView.apply {
            settings.apply {
                domStorageEnabled = true
                loadsImagesAutomatically = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptEnabled = true
            }
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
        }

        // Load URL into WebView
        if (newsUrl != null) {
            newsWebView.loadUrl(newsUrl)
        }
    }
}