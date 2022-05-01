package com.bizmiz.adepuz.ui.home.tasbeeh

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentTasbeehBinding


class TasbeehFragment : Fragment() {
    private lateinit var prefs: SharedPreferences
    private lateinit var binding: FragmentTasbeehBinding
    private lateinit var btnSound: MediaPlayer
    private var count = 0
    private var check = 0
    private var checkRakat = true
    private var total = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        prefs = requireActivity().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE)
        count = prefs.getInt("count",0)
        total = prefs.getInt("total",0)
        check = prefs.getInt("check",0)
        checkRakat = prefs.getBoolean("checkRakat",true)
        btnSound = MediaPlayer.create(requireContext(), R.raw.sound)
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = FragmentTasbeehBinding.bind(
            inflater.inflate(
                R.layout.fragment_tasbeeh,
                container,
                false
            )
        )
        if (check == 0) {
            binding.setVolume.setImageResource(R.drawable.volume_on)
        } else if (check == 1) {
            binding.setVolume.setImageResource(R.drawable.ic_vibration)
        } else if (check == 2) {
            binding.setVolume.setImageResource(R.drawable.volume_off)
        }
        if (checkRakat){
            binding.setCount.text = "33"
            binding.textView13.text = "/33"
        }else{
            binding.setCount.text = "99"
            binding.textView13.text = "/99"
        }
        binding.tvCurrentNumber.text = count.toString()
        binding.tvCurrentScore.text = count.toString()
        binding.tvScore.text = total.toString()
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.popBackStack()
        }
        binding.setVolume.setOnClickListener {
            if (check == 0) {
                binding.setVolume.setImageResource(R.drawable.ic_vibration)
                vibration(300)
                check = 1
                prefs.edit().putInt("check",1).apply()
            } else if (check == 1) {
                binding.setVolume.setImageResource(R.drawable.volume_off)
                check = 2
                prefs.edit().putInt("check",2).apply()
            } else if (check == 2) {
                btnSound.start()
                binding.setVolume.setImageResource(R.drawable.volume_on)
                check = 0
                prefs.edit().putInt("check",0).apply()
            }

        }
        binding.setCount.setOnClickListener {
            if (check == 0) {
                if (!btnSound.isPlaying) {
                    btnSound.start()
                }
            }else if (check ==1){
                vibration(300)
            }
            if (checkRakat){
                binding.setCount.text = "99"
                binding.textView13.text = "/99"
                checkRakat = false
                prefs.edit().putBoolean("checkRakat",false).apply()
            }else{
                binding.setCount.text = "33"
                binding.textView13.text = "/33"
                checkRakat = true
                prefs.edit().putBoolean("checkRakat",true).apply()
            }
        }
        binding.tvCurrentNumber.setOnClickListener {
            if (check == 0) {
                if (!btnSound.isPlaying) {
                    btnSound.start()
                    count++
                    total++
                    binding.tvCurrentNumber.text = count.toString()
                    binding.tvCurrentScore.text = count.toString()
                    binding.tvScore.text = total.toString()
                    prefs.edit().putInt("count",count).apply()
                    prefs.edit().putInt("total",total).apply()
                    if (checkRakat){
                        if (count == 33) {
                            vibration(300)
                        } else if (count == 34) {
                            count = 1
                            binding.tvCurrentNumber.text = "1"
                            binding.tvCurrentScore.text = "1"
                            prefs.edit().putInt("count",1).apply()
                        }
                    }else{
                        if (count == 99) {
                            vibration(300)
                        } else if (count == 100) {
                            count = 1
                            binding.tvCurrentNumber.text = "1"
                            binding.tvCurrentScore.text = "1"
                            prefs.edit().putInt("count",1).apply()
                        }
                    }
                }
            } else if (check == 1) {
                vibration(100)
            }
            if (!btnSound.isPlaying) {
                count++
                total++
                binding.tvCurrentNumber.text = count.toString()
                binding.tvCurrentScore.text = count.toString()
                binding.tvScore.text = total.toString()
                prefs.edit().putInt("count",count).apply()
                prefs.edit().putInt("total",total).apply()
                if (checkRakat){
                    if (count == 33 && check != 2) {
                        vibration(300)
                    } else if (count == 34) {
                        count = 1
                        binding.tvCurrentNumber.text = "1"
                        binding.tvCurrentScore.text = "1"
                        prefs.edit().putInt("count",1).apply()
                    }
                }else{
                    if (count == 99 && check != 2) {
                        vibration(300)
                    } else if (count == 100) {
                        count = 1
                        binding.tvCurrentNumber.text = "1"
                        binding.tvCurrentScore.text = "1"
                        prefs.edit().putInt("count",1).apply()
                    }
                }

            }
        }
        binding.replaceCount.setOnClickListener {
            if (check == 0) {
                if (!btnSound.isPlaying) {
                    btnSound.start()
                }
            }else if (check ==1){
                vibration(300)
            }
            count = 0
            total = 0
            binding.tvCurrentNumber.text = count.toString()
            binding.tvCurrentScore.text = count.toString()
            binding.tvScore.text = total.toString()
            prefs.edit().putInt("count",count).apply()
            prefs.edit().putInt("total",total).apply()
        }
        return binding.root
    }

    @SuppressLint("ServiceCast")
    private fun vibration(second: Long) {
        val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        second,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                ) // New vibrate method for API Level 26 or higher
            } else {
                vibrator.vibrate(second) // Vibrate method for below API Level 26
            }
        }
    }
}