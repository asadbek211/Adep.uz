package com.bizmiz.adepuz.ui.start.start_language

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentStartLanguageBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.orhanobut.hawk.Hawk

class StartLanguageFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentStartLanguageBinding
    private var lang = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).numberCheck = 3
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        binding = FragmentStartLanguageBinding.bind(
            inflater.inflate(
                R.layout.fragment_start_language,
                container,
                false
            )
        )
        binding.uzbLatin.setOnClickListener(this)
        binding.uzbKrill.setOnClickListener(this)
        binding.qrLatin.setOnClickListener(this)
        binding.qrKrill.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        when (lang) {
            0 -> {
                binding.uzbLatin.setBackgroundResource(R.drawable.shape_stroke)
                binding.uzbKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.qrLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.qrKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.tvTitle.text = "Tilni tanlang"
                binding.btnNext.text = "Keyingi"
            }
            1 -> {
                binding.uzbKrill.setBackgroundResource(R.drawable.shape_stroke)
                binding.uzbLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.qrLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.qrKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.tvTitle.text = "Тилни танланг"
                binding.btnNext.text = "Кейинги"
            }
            2 -> {
                binding.qrLatin.setBackgroundResource(R.drawable.shape_stroke)
                binding.uzbKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.uzbLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.qrKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.tvTitle.text = "Tildi saylań"
                binding.btnNext.text = "Keyingi"
            }
            3 -> {
                binding.qrKrill.setBackgroundResource(R.drawable.shape_stroke)
                binding.uzbKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.qrLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.uzbLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.tvTitle.text = "Тилди сайлаң"
                binding.btnNext.text = "Кейинги"
            }
        }
        return binding.root
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.uzb_latin -> {
                binding.uzbLatin.setBackgroundResource(R.drawable.shape_stroke)
                binding.uzbKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.qrLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.qrKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.tvTitle.text = "Tilni tanlang"
                binding.btnNext.text = "Keyingi"
                lang = 0
            }
            R.id.uzb_krill -> {
                binding.uzbKrill.setBackgroundResource(R.drawable.shape_stroke)
                binding.uzbLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.qrLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.qrKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.tvTitle.text = "Тилни танланг"
                binding.btnNext.text = "Кейинги"
                lang = 1
            }
            R.id.qr_latin -> {
                binding.qrLatin.setBackgroundResource(R.drawable.shape_stroke)
                binding.uzbKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.uzbLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.qrKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.tvTitle.text = "Tildi saylań"
                binding.btnNext.text = "Keyingi"
                lang = 2
            }
            R.id.qr_krill -> {
                binding.qrKrill.setBackgroundResource(R.drawable.shape_stroke)
                binding.uzbKrill.setBackgroundResource(R.drawable.settings_selector)
                binding.qrLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.uzbLatin.setBackgroundResource(R.drawable.settings_selector)
                binding.tvTitle.text = "Тилди сайлаң"
                binding.btnNext.text = "Кейинги"
                lang = 3
            }
            R.id.btnNext -> {
                when (lang) {
                    0 -> {
                        setLanguage("uz-rUZ")
                    }
                    1 -> {
                        setLanguage("es")
                    }
                    2 -> {
                        setLanguage("ru")
                    }
                    3 -> {
                        setLanguage("en")
                    }
                }
            }
        }
    }

    private fun setLanguage(id: String) {
        Hawk.put("pref_lang", id)
        val bundle = bundleOf(
            "number" to lang
        )
        val navController =
            Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
        navController.navigate(R.id.action_startLanguageFragment_to_startLocationFragment, bundle)
    }
}