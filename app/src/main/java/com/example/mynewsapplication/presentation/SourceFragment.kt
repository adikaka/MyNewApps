package com.example.mynewsapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mynewsapplication.R
import com.example.mynewsapplication.base.BaseFragment
import com.example.mynewsapplication.databinding.FragmentSourceBinding
import com.example.mynewsapplication.model.NewsModel
import com.example.mynewsapplication.presentation.adapter.SourceAdapter
import com.example.mynewsapplication.utils.Constants

class SourceFragment(private val newsDataList: MutableList<NewsModel>) : BaseFragment<FragmentSourceBinding>(){
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
                val fragment = ArticleFragment(newsDataList, uniqueDataSource[position])
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainAct, fragment)
                    .addToBackStack(null)
                    .commit()
//
//                // Dapatkan ViewPager yang sedang ditampilkan
//                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.view_pager)
//
//                // Dapatkan fragment sebelumnya yang ditampilkan dalam ViewPager
//                val previousPosition = viewPager.currentItem - 1
//                val previousFragment = childFragmentManager.findFragmentByTag("f$previousPosition") as? ArticleFragment
//
//                // Lakukan perubahan nilai source pada fragment sebelumnya
//                previousFragment?.updateSource(uniqueDataSource[position])
//
//                // Kembali ke fragment sebelumnya
//                viewPager.currentItem = previousPosition
            }
        })

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