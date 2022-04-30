package com.bizmiz.adepuz.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.adepuz.BuildConfig
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentProfileBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.bizmiz.adepuz.utils.Constant
import com.orhanobut.hawk.Hawk


class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).numberCheck = 3
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.purple_700)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        binding = FragmentProfileBinding.bind(
            inflater.inflate(
                R.layout.fragment_profile,
                container,
                false
            )
        )
        binding.favourite.setOnClickListener(this)
        binding.appLocation.setOnClickListener(this)
        binding.about.setOnClickListener(this)
        binding.privacyPolicy.setOnClickListener(this)
        binding.share.setOnClickListener(this)
        binding.settings.setOnClickListener(this)
        when(getLanguage()){
            "uz-rUZ","es"-> binding.imageView3.setImageResource(R.drawable.l_new_lotin)
            "ru","en"-> binding.imageView3.setImageResource(R.drawable.l_new_krill)
            else -> binding.imageView3.setImageResource(R.drawable.l_new_lotin)
        }
        return binding.root
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.favourite -> {
                navigation(R.id.action_profile_to_favouriteFragment)
            }
            R.id.appLocation -> {
                val navController =
                    Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
                navController.navigate(R.id.action_basicFragment_to_ourAddressFragment)
            }
            R.id.about -> {
                val navController =
                    Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
                navController.navigate(R.id.basicFragment_to_aboutFragment)
            }
            R.id.privacyPolicy -> {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.freeprivacypolicy.com/live/66044b9a-bde6-4d18-8a5c-2748d47cbec7")
                )
                startActivity(browserIntent)
            }
            R.id.share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Tavsiya qilaman: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
                )
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
            R.id.settings -> {
                val navController =
                    Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
                navController.navigate(R.id.action_basicFragment_to_settingsFragment2)
            }
        }
    }

    private fun navigation(id: Int) {
        val navController =
            Navigation.findNavController(requireActivity(), R.id.basicNavigation)
        navController.navigate(id)
    }
    private fun getLanguage():String {
        return Hawk.get("pref_lang",getString(R.string.o_zbek_lotin))

    }
}