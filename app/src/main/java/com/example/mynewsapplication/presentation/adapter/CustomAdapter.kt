package com.example.mynewsapplication.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapplication.NewsModel
import com.example.mynewsapplication.R
import com.example.mynewsapplication.databinding.ListItemBinding
import com.squareup.picasso.Picasso
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

class CustomAdapter(private var newsList: List<NewsModel>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var onClickListener: OnItemClickListener
    private lateinit var onLongClickListener: OnItemLongClickListener

    init {
        this.notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        onLongClickListener = listener
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding, onClickListener, onLongClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsData = newsList[holder.adapterPosition]

        holder.binding.newsTitle.text = newsData.headLine
        val time: String? = newsData.time
        val imgUrl = newsData.image

        if (imgUrl.isNullOrEmpty()) {
            Picasso.get()
                .load( R.drawable.samplenews)
                .fit()
                .centerCrop()
                .into(holder.binding.img)
        } else {
            Picasso.get()
                .load(imgUrl)
                .fit()
                .centerCrop()
                .error(R.drawable.samplenews)
                .into(holder.binding.img)
        }


        val currentTimeInHours = Instant.now().atZone(ZoneId.of("Asia/Jakarta"))
        val newsTimeInHours = Instant.parse(time).atZone(ZoneId.of("Asia/Jakarta"))
        val hoursDifference = Duration.between(currentTimeInHours, newsTimeInHours)
        val hoursAgo = " " + hoursDifference.toHours().toString().substring(1) + " hour ago"
        holder.binding.newsPublicationTime.text = hoursAgo

    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(
        val binding: ListItemBinding,
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