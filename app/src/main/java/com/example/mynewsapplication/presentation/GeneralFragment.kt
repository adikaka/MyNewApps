package com.example.mynewsapplication.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapplication.MainActivity
import com.example.mynewsapplication.NewsModel
import com.example.mynewsapplication.R
import com.example.mynewsapplication.ReadNewsActivity
import com.example.mynewsapplication.base.BaseFragment
import com.example.mynewsapplication.databinding.FragmentGeneralBinding
import com.example.mynewsapplication.presentation.ReadNewsFragment
import com.example.mynewsapplication.presentation.adapter.CustomAdapter
import com.example.mynewsapplication.utils.Constants.NEWS_CONTENT
import com.example.mynewsapplication.utils.Constants.NEWS_DESCRIPTION
import com.example.mynewsapplication.utils.Constants.NEWS_IMAGE_URL
import com.example.mynewsapplication.utils.Constants.NEWS_PUBLICATION_TIME
import com.example.mynewsapplication.utils.Constants.NEWS_SOURCE
import com.example.mynewsapplication.utils.Constants.NEWS_TITLE
import com.example.mynewsapplication.utils.Constants.NEWS_URL

class GeneralFragment : BaseFragment<FragmentGeneralBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGeneralBinding {
        return FragmentGeneralBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        val newsData: MutableList<NewsModel> = MainActivity.generalNews
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = CustomAdapter(newsData)
        recyclerView.adapter = adapter


        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val fragment = ReadNewsFragment()
                val newsData = MainActivity.generalNews[position]
                val args = Bundle().apply {
                    putString(NEWS_URL, newsData.url)
                    putString(NEWS_TITLE, newsData.headLine)
                    putString(NEWS_IMAGE_URL, newsData.image)
                    putString(NEWS_DESCRIPTION, newsData.description)
                    putString(NEWS_SOURCE, newsData.source)
                    putString(NEWS_PUBLICATION_TIME, newsData.time)
                    putString(NEWS_CONTENT, newsData.content)
                }
                fragment.arguments = args
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainAct, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        // ignore
        adapter.setOnItemLongClickListener(object : CustomAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })
    }

    private fun createIntent(newsData: NewsModel): Intent {
        return Intent(context, ReadNewsActivity::class.java).apply {
            putExtra(NEWS_URL, newsData.url)
            putExtra(NEWS_TITLE, newsData.headLine)
            putExtra(NEWS_IMAGE_URL, newsData.image)
            putExtra(NEWS_DESCRIPTION, newsData.description)
            putExtra(NEWS_SOURCE, newsData.source)
            putExtra(NEWS_PUBLICATION_TIME, newsData.time)
            putExtra(NEWS_CONTENT, newsData.content)
        }
    }
}