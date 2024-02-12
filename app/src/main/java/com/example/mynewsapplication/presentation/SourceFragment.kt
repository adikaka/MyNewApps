package com.example.mynewsapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapplication.base.BaseFragment
import com.example.mynewsapplication.databinding.FragmentSourceBinding
import com.example.mynewsapplication.model.NewsModel
import com.example.mynewsapplication.presentation.adapter.SourceAdapter

class SourceFragment(private val newsDataList: MutableList<NewsModel>, private val newsFilterer: (String) -> Unit) : BaseFragment<FragmentSourceBinding>(){
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSourceBinding {
        return FragmentSourceBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        val recyclerView : RecyclerView = binding.recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        var uniqueDataSource: MutableList<String> = getUniqueSource(newsDataList)
        val adapter = SourceAdapter(uniqueDataSource)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : SourceAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context, uniqueDataSource[position], Toast.LENGTH_SHORT).show()
//                val fragment = ArticleFragment(newsDataList, uniqueDataSource[position])

                newsFilterer.invoke(uniqueDataSource[position])
                parentFragmentManager.popBackStack()
            }
        })

        binding.resetSource.setOnClickListener{
            val filter: String = ""
            newsFilterer.invoke(filter)
            parentFragmentManager.popBackStack()
        }

        // Ignore
        adapter.setOnItemLongClickListener(object : SourceAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })
    }

    fun getUniqueSource(newsList: MutableList<NewsModel>): MutableList<String> {
        val uniqueSources = HashSet<String>()

        for (news in newsList) {
            news.source?.let { uniqueSources.add(it) }
        }

        return uniqueSources.toMutableList()
    }
}