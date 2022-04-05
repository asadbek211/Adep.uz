package com.bizmiz.adepuz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.BesUstunItemBinding
import com.bizmiz.adepuz.databinding.HomeItemNewsBinding
import com.bizmiz.adepuz.databinding.HomeItemPostsBinding

class BesUstunAdapter : RecyclerView.Adapter<BesUstunAdapter.Myholder>() {
    private val image: ArrayList<Int> =
        arrayListOf(
            R.drawable.iyman,
            R.drawable.namaz,
            R.drawable.oroza,
            R.drawable.zakat,
            R.drawable.xaj
        )
    private val textList: ArrayList<String> =
        arrayListOf(
            "Iyman",
            "Namaz",
            "Oraza",
            "Zakat",
            "Xaj"
        )
    inner class Myholder(private val binding: BesUstunItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(position: Int) {
           binding.itemImg.setImageResource(image[position])
            binding.itemTxt.text = textList[position]
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val besUstunItemBinding =
            BesUstunItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(besUstunItemBinding)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.populateModel(position)
    }

    override fun getItemCount(): Int = image.size
}