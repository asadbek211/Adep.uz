package com.bizmiz.adepuz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.adepuz.databinding.HomeItemPostsBinding
import com.bizmiz.adepuz.model.ArticlesData
import com.bumptech.glide.Glide

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.Myholder>() {
    var postsList: List<ArticlesData> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class Myholder(private val binding: HomeItemPostsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(articlesData: ArticlesData) {
            Glide.with(binding.root.context).load("https://adep.uz/${articlesData.image}")
                .into(binding.homeItemNewsImage)
            binding.homeItemNewsText.text = articlesData.title
            binding.homeItemNewsEye.text = articlesData.views
            binding.cardView.setOnClickListener {
                onclick.invoke(articlesData)
            }
        }

    }

    private var onclick: (articlesData: ArticlesData) -> Unit = {}
    fun onClickListener(onclick: (articlesData: ArticlesData) -> Unit) {
        this.onclick = onclick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val homeItemPostsBinding =
            HomeItemPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(homeItemPostsBinding)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(postsList[position])
    }

    override fun getItemCount(): Int = postsList.size
}