package com.example.mynewsapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapplication.model.NewsModel
import com.example.mynewsapplication.R
import com.example.mynewsapplication.base.BaseFragment
import com.example.mynewsapplication.data.repository.NewsRepository
import com.example.mynewsapplication.databinding.FragmentArticleBinding
import com.example.mynewsapplication.presentation.adapter.ArticleAdapter
import com.example.mynewsapplication.utils.Constants.NEWS_CONTENT
import com.example.mynewsapplication.utils.Constants.NEWS_DESCRIPTION
import com.example.mynewsapplication.utils.Constants.NEWS_IMAGE_URL
import com.example.mynewsapplication.utils.Constants.NEWS_PUBLICATION_TIME
import com.example.mynewsapplication.utils.Constants.NEWS_SOURCE
import com.example.mynewsapplication.utils.Constants.NEWS_TITLE
import com.example.mynewsapplication.utils.Constants.NEWS_URL
import com.example.mynewsapplication.viewmodel.ArticleViewModel

class ArticleFragment(private val newsDataList: MutableList<NewsModel>, private var filterSource: String): BaseFragment<FragmentArticleBinding>(){
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentArticleBinding {
        return FragmentArticleBinding.inflate(inflater, container, false)
    }

    private lateinit var articleViewModel: ArticleViewModel

    override fun setupView() {
        articleViewModel = ArticleViewModel(NewsRepository())

        val recyclerView : RecyclerView = binding.recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val dataFilterSource : MutableList<NewsModel> = articleViewModel.getNewsWithSource(newsDataList, filterSource)
        val adapter = ArticleAdapter(dataFilterSource)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : ArticleAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val fragment = ReadNewsFragment()
                val newData = dataFilterSource[position]
                val args = Bundle().apply {
                    putString(NEWS_URL, newData.url)
                    putString(NEWS_TITLE, newData.headLine)
                    putString(NEWS_IMAGE_URL, newData.image)
                    putString(NEWS_DESCRIPTION, newData.description)
                    putString(NEWS_SOURCE, newData.source)
                    putString(NEWS_PUBLICATION_TIME, newData.time)
                    putString(NEWS_CONTENT, newData.content)
                }
                fragment.arguments = args
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainAct, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.componentBottomSource.btnSource.setOnClickListener {
            val newsFilterer: (String) -> Unit = { selectedSource ->
                val filteredNews =  articleViewModel.getNewsWithSource(newsDataList, selectedSource)
                adapter.updateNewsList(filteredNews)
            }

            val fragment = SourceFragment(newsDataList, newsFilterer)
            parentFragmentManager.beginTransaction()
                .add(R.id.mainAct, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.svSearch.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null){
                    return true
                }
                val dataFilterSearch : MutableList<NewsModel> = articleViewModel.getNewsWithSearch(newsDataList,newText)
                adapter.updateNewsList(dataFilterSearch)
                return true
            }

        })

        // Ignore
        adapter.setOnItemLongClickListener(object : ArticleAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })
    }
}