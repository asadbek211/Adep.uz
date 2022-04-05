package com.bizmiz.adepuz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.HomeItemPostsBinding
import com.bizmiz.adepuz.model.PostDataItem
import com.bumptech.glide.Glide

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.Myholder>() {
     var postsList: ArrayList<PostDataItem> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class Myholder(private val binding: HomeItemPostsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(postDataItem: PostDataItem,position: Int) {
            Glide.with(binding.root.context).load("https://adep.uz/${postDataItem.image}")
                .into(binding.homeItemNewsImage)
            binding.homeItemNewsText.text = postDataItem.title
            binding.homeItemNewsEye.text = postDataItem.views
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val homeItemPostsBinding =
            HomeItemPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(homeItemPostsBinding)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(postsList[position],position)
    }

    override fun getItemCount(): Int = postsList.size
}