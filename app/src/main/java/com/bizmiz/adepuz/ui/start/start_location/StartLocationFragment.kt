package com.bizmiz.adepuz.ui.start.start_location

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentStartLocationBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.bizmiz.adepuz.utils.LocaleManager

class StartLocationFragment : Fragment() {
    private lateinit var binding: FragmentStartLocationBinding
    private lateinit var prefs: SharedPreferences
    private var lang: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        prefs = requireActivity().getSharedPreferences("MY_START", Context.MODE_PRIVATE)
        lang = requireArguments().getInt("number", 0)
        (activity as MainActivity).numberCheck = 3
        binding = FragmentStartLocationBinding.bind(
            inflater.inflate(
                R.layout.fragment_start_location,
                container,
                false
            )
        )
        requestPermission()
        setText(lang)
        binding.btnNext.setOnClickListener {
            if (checkPermission()){
                val navController =
                    Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
                navController.navigate(R.id.action_startLocationFragment_to_basicFragment)
                LocaleManager.setLocale(requireContext())
                prefs.edit().putBoolean("start",true).apply()
            }else{
                Toast.makeText(requireActivity(), "Joylashuv olinmadi", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), 1010
        )
    }

    private fun setText(lang: Int) {
        when (lang) {
            0 -> {
                binding.tvTitle.text =
                    "Namoz vaqtlarini hisoblash uchun joylashuvingizni aniqlash lozim"
                binding.btnNext.text = "Joylashuvni aniqlash"
            }
            1 -> {
                binding.tvTitle.text =
                    "Намоз вақтларини ҳисоблаш учун жойлашувингизни аниқлаш лозим"
                binding.btnNext.text = "Жойлашувни аниқлаш"
            }
            2 -> {
                binding.tvTitle.text =
                    "Namaz waqıtların esaplaw ushın jaylasıwıńızdı anıqlaw kerek"
                binding.btnNext.text = "Jaylasıwdı anıqlaw"
            }
            3 -> {
                binding.tvTitle.text =
                    "Намаз ўақытларын есаплаў ушын жайласыўыңызды анықлаў керек"
                binding.btnNext.text = "Жайласыўды анықлаў"
            }
        }
    }
    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
    }
}