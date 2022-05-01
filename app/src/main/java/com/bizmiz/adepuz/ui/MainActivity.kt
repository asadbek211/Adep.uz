package com.bizmiz.adepuz.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.ActivityMainBinding
import com.bizmiz.adepuz.utils.LocaleManager

class MainActivity : AppCompatActivity() {
    var numberCheck = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }

    override fun onBackPressed() {
        when (numberCheck) {
            2 -> {
                val navController =
                    Navigation.findNavController(this, R.id.basicNavigation)
                navController.popBackStack()
            }
            3 -> {
                super.onBackPressed()
            }
        }
    }
}