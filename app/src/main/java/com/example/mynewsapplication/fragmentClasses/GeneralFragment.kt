package com.example.newsapp.fragmentClasses

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapplication.MainActivity
import com.example.mynewsapplication.NewsModel
import com.example.mynewsapplication.R
import com.example.mynewsapplication.ReadNewsActivity
import com.example.mynewsapplication.adapter.CustomAdapter
import com.example.mynewsapplication.utils.Constants.INITIAL_POSITION
import com.example.mynewsapplication.utils.Constants.NEWS_CONTENT
import com.example.mynewsapplication.utils.Constants.NEWS_DESCRIPTION
import com.example.mynewsapplication.utils.Constants.NEWS_IMAGE_URL
import com.example.mynewsapplication.utils.Constants.NEWS_PUBLICATION_TIME
import com.example.mynewsapplication.utils.Constants.NEWS_SOURCE
import com.example.mynewsapplication.utils.Constants.NEWS_TITLE
import com.example.mynewsapplication.utils.Constants.NEWS_URL
import com.example.mynewsapplication.utils.Constants.TOP_HEADLINES_COUNT
//import com.jama.carouselview.CarouselView
//import com.jama.carouselview.enums.IndicatorAnimationType
//import com.jama.carouselview.enums.OffsetType
import com.squareup.picasso.Picasso

class GeneralFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewSource: RecyclerView
//    private lateinit var carouselView: CarouselView
    private lateinit var adapter: CustomAdapter
//    private lateinit var newsDataForTopHeadlines: List<NewsModel>
    private lateinit var newsDataForDown: List<NewsModel>
    private lateinit var newDataForSource: List<NewsModel>
    var position = INITIAL_POSITION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_general, container, false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val layoutManagerSource = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        recyclerViewSource = view.findViewById(R.id.sourceRecyclerView)
        recyclerViewSource.layoutManager = layoutManagerSource

        // Setting recyclerViews adapter
//        newsDataForTopHeadlines = MainActivity.generalNews.slice(0 until TOP_HEADLINES_COUNT)
        newsDataForDown = MainActivity.generalNews.slice(TOP_HEADLINES_COUNT until MainActivity.generalNews.size - TOP_HEADLINES_COUNT)
        adapter = CustomAdapter(newsDataForDown)
        recyclerView.adapter = adapter


        // listitem onClick
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ReadNewsActivity::class.java).apply {
                    putExtra(NEWS_URL, newsDataForDown[position].url)
                    putExtra(NEWS_TITLE, newsDataForDown[position].headLine)
                    putExtra(NEWS_IMAGE_URL, newsDataForDown[position].image)
                    putExtra(NEWS_DESCRIPTION, newsDataForDown[position].description)
                    putExtra(NEWS_SOURCE, newsDataForDown[position].source)
                    putExtra(NEWS_PUBLICATION_TIME, newsDataForDown[position].time)
                    putExtra(NEWS_CONTENT, newsDataForDown[position].content)
                }

                startActivity(intent)
            }
        })

        // Ignore
        adapter.setOnItemLongClickListener(object : CustomAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })

        return view
    }

}