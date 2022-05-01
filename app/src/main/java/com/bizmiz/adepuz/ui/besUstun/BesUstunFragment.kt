package com.bizmiz.adepuz.ui.besUstun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.adapters.BesUstunAdapter
import com.bizmiz.adepuz.databinding.FragmentBesUstunBinding
import com.bizmiz.adepuz.ui.MainActivity

class BesUstunFragment : Fragment() {
    private lateinit var besUstunAdapter: BesUstunAdapter
    private lateinit var binding: FragmentBesUstunBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        (activity as MainActivity).numberCheck = 3
        binding = FragmentBesUstunBinding.bind(inflater.inflate(R.layout.fragment_bes_ustun, container, false))
       besUstunAdapter = BesUstunAdapter()
        binding.itemRecyclerview.adapter = besUstunAdapter
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.popBackStack()
        }
        besUstunAdapter.onClickListener {position,text:String->
            val bundle = bundleOf(
                "text" to text
            )
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.navigate(R.id.action_besUstunFragment_to_containerFragment,bundle)
        }
        return binding.root
    }
}