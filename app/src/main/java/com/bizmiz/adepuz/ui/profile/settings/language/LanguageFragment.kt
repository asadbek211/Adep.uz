package com.bizmiz.adepuz.ui.profile.settings.language

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentLanguageBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.bizmiz.adepuz.utils.LocaleManager
import com.orhanobut.hawk.Hawk

class LanguageFragment : Fragment() {
    private lateinit var binding: FragmentLanguageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        (activity as MainActivity).numberCheck = 2
        binding = FragmentLanguageBinding.bind(inflater.inflate(R.layout.fragment_language, container, false))
        binding.tvBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.popBackStack()
        }
        binding.uzbLatin.setOnClickListener {
            setLanguage("uz-rUZ")
        }
        binding.uzbKrill.setOnClickListener {
            setLanguage("es")
        }
        binding.qrLatin.setOnClickListener {
            setLanguage("ru")
        }
        binding.qrKrill.setOnClickListener {
            setLanguage("en")
        }
        return binding.root
    }
    private fun setLanguage(id: String) {
        Hawk.put("pref_lang", id)
        LocaleManager.setLocale(requireContext())
        val navController =
            Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
        navController.popBackStack()
    }
}