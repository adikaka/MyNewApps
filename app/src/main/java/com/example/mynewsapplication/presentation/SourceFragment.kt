package com.example.mynewsapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapplication.base.BaseFragment
import com.example.mynewsapplication.data.repository.NewsRepository
import com.example.mynewsapplication.databinding.FragmentSourceBinding
import com.example.mynewsapplication.model.NewsModel
import com.example.mynewsapplication.presentation.adapter.SourceAdapter
import com.example.mynewsapplication.viewmodel.SourceViewModel

class SourceFragment(private val newsDataList: MutableList<NewsModel>, private val newsFilterer: (String) -> Unit) : BaseFragment<FragmentSourceBinding>(){
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSourceBinding {
        return FragmentSourceBinding.inflate(inflater, container, false)
    }

    private lateinit var sourceViewModel: SourceViewModel
    override fun setupView() {
        sourceViewModel = SourceViewModel(NewsRepository())

        val recyclerView : RecyclerView = binding.recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        var uniqueDataSource: MutableList<String> = sourceViewModel.getUniqueSource(newsDataList)
        val adapter = SourceAdapter(uniqueDataSource)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : SourceAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context, uniqueDataSource[position], Toast.LENGTH_SHORT).show()
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
}