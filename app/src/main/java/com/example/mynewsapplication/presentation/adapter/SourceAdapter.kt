package com.example.mynewsapplication.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapplication.databinding.ListItemSourceBinding

class SourceAdapter(private var newsList: MutableList<String>) :
    RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var onClickListener: SourceAdapter.OnItemClickListener
    private lateinit var onLongClickListener: SourceAdapter.OnItemLongClickListener

    init {
        this.notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    fun setOnItemLongClickListener(listener: SourceAdapter.OnItemLongClickListener) {
        onLongClickListener = listener
    }

    fun setOnItemClickListener(listener: SourceAdapter.OnItemClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceAdapter.ViewHolder {
        val binding = ListItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return SourceAdapter.ViewHolder(binding, onClickListener, onLongClickListener)
    }

    override fun onBindViewHolder(holder: SourceAdapter.ViewHolder, position: Int) {
        val newsData = newsList[holder.position]
        holder.binding.newsTitleSource.text = newsData
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(
        val binding: ListItemSourceBinding,
        listener: OnItemClickListener,
        listener2: OnItemLongClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

            binding.root.setOnLongClickListener {
                listener2.onItemLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

    }

}