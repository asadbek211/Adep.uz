package com.bizmiz.adepuz.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.adepuz.databinding.HomeItemNewsBinding
import com.bizmiz.adepuz.model.NewsData
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.Instant


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.Myholder>() {
    var newsList: List<NewsData> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class Myholder(private val binding: HomeItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun populateModel(newsData: NewsData) {
            val instant = Instant.parse(newsData.published_at)
            val mills: Long = instant.toEpochMilli()

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateString = simpleDateFormat.format(mills)
            Glide.with(binding.root.context).load("https://adep.uz/${newsData.image}")
                .into(binding.itemNewsImage)
            binding.itemNewsName.text = newsData.title
            binding.newsEyeCount.text = newsData.views
            binding.publishData.text = String.format("%s", dateString)
            binding.homeCard.setOnClickListener {
                onclick.invoke(newsData)
            }
        }

    }

    private var onclick: (newsData: NewsData) -> Unit = {}
    fun onClickListener(onclick: (newsData: NewsData) -> Unit) {
        this.onclick = onclick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val homeItemNewsBinding =
            HomeItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(homeItemNewsBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size
}