package com.bizmiz.adepuz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.BesUstunItemBinding
import com.bizmiz.adepuz.model.PostsData

class BesUstunAdapter : RecyclerView.Adapter<BesUstunAdapter.Myholder>() {
    private val image: ArrayList<Int> =
        arrayListOf(
            R.drawable.iyman,
            R.drawable.namaz,
            R.drawable.oroza,
            R.drawable.zakat,
            R.drawable.xaj
        )

    inner class Myholder(private val binding: BesUstunItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(position: Int) {
             val textList: ArrayList<String> =
                arrayListOf(
                    binding.root.context.getString(R.string.iyman),
                    binding.root.context.getString(R.string.namaz),
                    binding.root.context.getString(R.string.oroza),
                    binding.root.context.getString(R.string.zakat),
                    binding.root.context.getString(R.string.xaj)
                )
           binding.itemImg.setImageResource(image[position])
            binding.itemTxt.text = textList[position]
            binding.homeNearbyCard.setOnClickListener {
                onclick.invoke(position,textList[position])
            }
        }

    }
    private var onclick: (position:Int,text:String) -> Unit = { position: Int, text: String -> }
    fun onClickListener(onclick: (position:Int,text:String) -> Unit) {
        this.onclick = onclick
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