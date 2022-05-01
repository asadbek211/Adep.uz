package com.bizmiz.adepuz.ui.profile.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentAboutBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.orhanobut.hawk.Hawk

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        (activity as MainActivity).numberCheck = 3
        binding = FragmentAboutBinding.bind(inflater.inflate(R.layout.fragment_about, container, false))
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.popBackStack()
        }
        binding.tvDeveloper.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Satimov_A"))
            startActivity(browserIntent)
        }
        when(getLanguage()){
            "uz-rUZ","es"-> binding.imageView4.setImageResource(R.drawable.l_new_lotin)
            "ru","en"-> binding.imageView4.setImageResource(R.drawable.l_new_krill)
            else -> binding.imageView4.setImageResource(R.drawable.l_new_lotin)
        }
        return binding.root
    }
    private fun getLanguage():String {
        return Hawk.get("pref_lang",getString(R.string.o_zbek_lotin))

    }
}