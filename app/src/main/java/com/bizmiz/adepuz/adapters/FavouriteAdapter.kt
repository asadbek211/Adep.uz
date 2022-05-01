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
import com.bizmiz.adepuz.databinding.ItemFavouriteBinding
import com.bizmiz.adepuz.model.PostsData
import com.bumptech.glide.Glide


class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.Myholder>() {
    private lateinit var prefs:SharedPreferences
    var postsList: List<PostsData> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class Myholder(private val binding: ItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun populateModel(postsData: PostsData) {
            prefs = binding.root.context.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE)
            Glide.with(binding.root.context).load("https://adep.uz/${postsData.image}")
                .into(binding.homeItemNewsImage)
            binding.homeItemNewsText.text = postsData.title
            binding.homeItemNewsEye.text = postsData.views
            if (getPostId(postsData.id)){
                binding.itemFavourite.setImageResource(R.drawable.ic_baseline_favorite_white)
            }else{
                binding.itemFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            binding.itemFavourite.setOnClickListener {
                if (getPostId(postsData.id)){
                    binding.itemFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    removePostId(postsData.id)
                }else{
                    binding.itemFavourite.setImageResource(R.drawable.ic_baseline_favorite_white)
                    setPostId(postsData.id)
                }
            }
            binding.cardView.setOnClickListener {
                onclick.invoke(postsData)
            }
        }

    }
    private fun setPostId(id: Int) {
        prefs.edit().putInt(id.toString(),id).apply()
    }
    private fun getPostId(id: Int): Boolean {
        return prefs.contains(id.toString())
    }
    private fun removePostId(id: Int){
        prefs.edit().remove(id.toString()).apply()
    }
    private var onclick: (postsData: PostsData) -> Unit = {}
    fun onClickListener(onclick: (postsData: PostsData) -> Unit) {
        this.onclick = onclick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val itemFavouriteBinding =
            ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(itemFavouriteBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(postsList[position])
    }

    override fun getItemCount(): Int = postsList.size
}