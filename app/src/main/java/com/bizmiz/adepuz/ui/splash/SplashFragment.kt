package com.bizmiz.adepuz.ui.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var playAlpha: Animation
    private lateinit var right: Animation
    private lateinit var prefs: SharedPreferences
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        prefs = requireActivity().getSharedPreferences("MY_START", Context.MODE_PRIVATE)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.purple_500)
        right = AnimationUtils.loadAnimation(requireContext(), R.anim.right)
        playAlpha = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha)
        binding =
            FragmentSplashBinding.bind(inflater.inflate(R.layout.fragment_splash, container, false))
        binding.logoContainer.startAnimation(right)
        right.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
               binding.logo.visibility = View.VISIBLE
               binding.logo.startAnimation(playAlpha)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        playAlpha.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                if (prefs.getBoolean("start",false)){
                    val navController =
                        Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
                    navController.navigate(R.id.action_splashFragment_to_basicFragment)
                }else{
                    val navController =
                        Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
                    navController.navigate(R.id.action_splashFragment_to_startLanguageFragment)
                }

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        return binding.root
    }
}