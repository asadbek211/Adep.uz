package com.bizmiz.adepuz.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentProfileBinding
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentProfileBinding.bind(inflater.inflate(R.layout.fragment_profile, container, false))
        return binding.root
    }
}