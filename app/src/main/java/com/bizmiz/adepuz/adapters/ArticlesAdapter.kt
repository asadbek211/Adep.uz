package com.bizmiz.adepuz.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.HomeItemPostsBinding
import com.bizmiz.adepuz.databinding.ItemArticlesBinding
import com.bizmiz.adepuz.databinding.ItemFavouriteBinding
import com.bizmiz.adepuz.model.ArticlesData
import com.bizmiz.adepuz.model.PostsData
import com.bumptech.glide.Glide


class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.Myholder>() {
    var articlesList: List<ArticlesData> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class Myholder(private val binding: ItemArticlesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
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
        val itemArticlesBinding =
            ItemArticlesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(itemArticlesBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(articlesList[position])
    }

    override fun getItemCount(): Int = articlesList.size
}