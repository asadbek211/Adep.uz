package com.bizmiz.adepuz.ui.profile.settings

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentSettingsBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.orhanobut.hawk.Hawk

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).numberCheck = 2
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        binding = FragmentSettingsBinding.bind(
            inflater.inflate(
                R.layout.fragment_settings,
                container,
                false
            )
        )
        binding.settingsLanguageText.text = when(getLanguage()){
            "uz-rUZ"-> getString(R.string.o_zbek_lotin)
            "es"-> getString(R.string.o_zbek_krill)
            "ru"-> getString(R.string.qaraqalpaq_lotin)
            "en"-> getString(R.string.qaralpaq_krill)
            getString(R.string.o_zbek_lotin)-> getString(R.string.o_zbek_lotin)
            else -> ""
        }
        binding.tvBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.popBackStack()
        }
        binding.profileSettingsLanguage.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.navigate(R.id.action_settingsFragment2_to_languageFragment2)
        }
        return binding.root
    }
    private fun getLanguage():String {
        return Hawk.get("pref_lang",getString(R.string.o_zbek_lotin))

    }
}