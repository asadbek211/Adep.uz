package com.bizmiz.adepuz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.HomeItemNewsBinding
import com.bizmiz.adepuz.databinding.HomeItemPostsBinding
import com.bizmiz.adepuz.model.PostDataItem
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.Myholder>() {
    var newsList: ArrayList<PostDataItem> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    inner class Myholder(private val binding: HomeItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(postDataItem: PostDataItem,position: Int) {
            Glide.with(binding.root.context).load("https://adep.uz/${postDataItem.image}")
                .into(binding.itemNewsImage)
            binding.itemNewsName.text = postDataItem.title
            binding.newsEyeCount.text = postDataItem.views
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val homeItemNewsBinding =
            HomeItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(homeItemNewsBinding)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(newsList[position],position)
    }

    override fun getItemCount(): Int = newsList.size
}