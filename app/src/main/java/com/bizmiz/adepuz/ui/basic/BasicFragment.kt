package com.bizmiz.adepuz.ui.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentBasicBinding

class BasicFragment : Fragment() {
    private lateinit var binding: FragmentBasicBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentBasicBinding.bind(inflater.inflate(R.layout.fragment_basic, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(requireActivity(), R.id.basicNavigation)
        binding.apply {
            bottomNavView.setupWithNavController(navController)
            bottomNavView.background = null
            fab.setOnClickListener {
                val navControl = Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
                navControl.navigate(R.id.action_basicFragment_to_besUstunFragment)
            }
        }
    }
}