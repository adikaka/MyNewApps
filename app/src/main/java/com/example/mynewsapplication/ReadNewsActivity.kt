package com.example.mynewsapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.view.Menu
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.mynewsapplication.databinding.ActivityReadNewsBinding
import com.example.mynewsapplication.viewmodel.NewsViewModel
import com.example.mynewsapplication.utils.Constants.NEWS_CONTENT
import com.example.mynewsapplication.utils.Constants.NEWS_DESCRIPTION
import com.example.mynewsapplication.utils.Constants.NEWS_IMAGE_URL
import com.example.mynewsapplication.utils.Constants.NEWS_PUBLICATION_TIME
import com.example.mynewsapplication.utils.Constants.NEWS_SOURCE
import com.example.mynewsapplication.utils.Constants.NEWS_TITLE
import com.example.mynewsapplication.utils.Constants.NEWS_URL
import java.util.ArrayList
import java.util.Locale

class ReadNewsActivity : AppCompatActivity(){

    private lateinit var binding: ActivityReadNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: ArrayList<NewsModel>

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReadNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val newsWebView = binding.newswebview
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        //loading data into list
        newsData = ArrayList(1)
        val newsUrl = intent.getStringExtra(NEWS_URL)
        val newsContent =
            intent.getStringExtra(NEWS_CONTENT) + ". get paid version to hear full news. "
        newsData.add(
            NewsModel(
                intent.getStringExtra(NEWS_TITLE)!!,
                intent.getStringExtra(NEWS_IMAGE_URL),
                intent.getStringExtra(NEWS_DESCRIPTION),
                newsUrl,
                intent.getStringExtra(NEWS_SOURCE),
                intent.getStringExtra(NEWS_PUBLICATION_TIME),
                newsContent
            )
        )

        // Webview
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


        if (newsUrl != null) {
            newsWebView.loadUrl(newsUrl)
        }

    }
}