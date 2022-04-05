package com.bizmiz.adepuz.ui.besUstun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.adapter.BesUstunAdapter
import com.bizmiz.adepuz.databinding.FragmentBesUstunBinding

class BesUstunFragment : Fragment() {
    private lateinit var besUstunAdapter: BesUstunAdapter
    private lateinit var binding: FragmentBesUstunBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentBesUstunBinding.bind(inflater.inflate(R.layout.fragment_bes_ustun, container, false))
       besUstunAdapter = BesUstunAdapter()
        binding.itemRecyclerview.adapter = besUstunAdapter
        return binding.root
    }
}