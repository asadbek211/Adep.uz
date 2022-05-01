package com.bizmiz.adepuz.ui.profile.address

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentOurAddressBinding
import com.orhanobut.hawk.Hawk

class OurAddressFragment : Fragment() {
    private lateinit var binding: FragmentOurAddressBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOurAddressBinding.bind(
            inflater.inflate(
                R.layout.fragment_our_address,
                container,
                false
            )
        )
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.popBackStack()
        }
        binding.ivTelegram.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/adep_uz"))
            startActivity(browserIntent)
        }
        binding.ivFacebook.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/adepuz.official"))
            startActivity(browserIntent)
        }
        binding.ivSite.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://adep.uz/"))
            startActivity(browserIntent)
        }
        binding.ivInstagram.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/adepuz.official"))
            startActivity(browserIntent)
        }
        binding.ivYoutube.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCz1Q2UkMCPfBJs2cQJ9uPYQ/"))
            startActivity(browserIntent)
        }
        return binding.root
    }
}