package com.example.mynewsapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mynewsapplication.databinding.ActivityMainBinding
import com.example.mynewsapplication.presentation.adapter.FragmentAdapter
import com.example.mynewsapplication.viewmodel.NewsViewModel
import com.example.mynewsapplication.utils.Constants.BUSINESS
import com.example.mynewsapplication.utils.Constants.ENTERTAINMENT
import com.example.mynewsapplication.utils.Constants.GENERAL
import com.example.mynewsapplication.utils.Constants.HEALTH
import com.example.mynewsapplication.utils.Constants.SCIENCE
import com.example.mynewsapplication.utils.Constants.SPORTS
import com.example.mynewsapplication.utils.Constants.TECHNOLOGY
import com.example.mynewsapplication.utils.Constants.TOTAL_NEWS_TAB
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    // Tabs Title
    private val newsCategories = arrayOf(
        GENERAL, BUSINESS,
        ENTERTAINMENT, SCIENCE,
        SPORTS, TECHNOLOGY, HEALTH
    )

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentAdapter: FragmentAdapter
    private var totalRequestCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Action Bar
        setSupportActionBar(binding.toolbar)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        if (!isNetworkAvailable(applicationContext)) {
            binding.shimmerLayout.visibility = View.GONE
            binding.displayError.text = getString(R.string.internet_warming)
            binding.displayError.visibility = View.VISIBLE
        }

        // Send request call for news data
        requestNews(GENERAL, generalNews)
        requestNews(BUSINESS, businessNews)
        requestNews(ENTERTAINMENT, entertainmentNews)
        requestNews(HEALTH, healthNews)
        requestNews(SCIENCE, scienceNews)
        requestNews(SPORTS, sportsNews)
        requestNews(TECHNOLOGY, techNews)

        fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = fragmentAdapter
        binding.viewPager.visibility = View.GONE

    }


    private fun requestNews(newsCategory: String, newsData: MutableList<NewsModel>) {
        viewModel.getNews(category = newsCategory)?.observe(this) {
            newsData.addAll(it)
            totalRequestCount += 1

            // If main fragment loaded then attach the fragment to viewPager
            if (newsCategory == GENERAL) {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.hideShimmer()
                binding.shimmerLayout.visibility = View.GONE
                setViewPager()
            }

            if (totalRequestCount == TOTAL_NEWS_TAB) {
                binding.viewPager.offscreenPageLimit = 7
            }
        }
    }

    private fun setViewPager() {
        //handle error API
        if (!apiRequestError) {
            binding.viewPager.visibility = View.VISIBLE
            TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
                tab.text = newsCategories[position]
            }.attach()
        } else {
            binding.displayError.text = errorMessage
            binding.displayError.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item_mainactivity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Check internet connection
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // For 29 api or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            // For below 29 api
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }

    companion object {
        var generalNews: MutableList<NewsModel> = mutableListOf()
        var entertainmentNews: MutableList<NewsModel> = mutableListOf()
        var businessNews: MutableList<NewsModel> = mutableListOf()
        var healthNews: MutableList<NewsModel> = mutableListOf()
        var scienceNews: MutableList<NewsModel> = mutableListOf()
        var sportsNews: MutableList<NewsModel> = mutableListOf()
        var techNews: MutableList<NewsModel> = mutableListOf()
        var apiRequestError = false
        var errorMessage = "error"
    }
}