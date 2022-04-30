package com.bizmiz.adepuz.ui.besUstun.conatainer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentContainerBinding
import com.bizmiz.adepuz.ui.MainActivity

class ContainerFragment : Fragment() {
    private lateinit var binding: FragmentContainerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        (activity as MainActivity).numberCheck = 3
        val titleText = requireArguments().getString("text","")
        binding = FragmentContainerBinding.bind(inflater.inflate(R.layout.fragment_container, container, false))
        binding.title.text = titleText
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.popBackStack()
        }
        return binding.root
    }
}