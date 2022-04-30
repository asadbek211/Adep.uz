package com.bizmiz.adepuz.ui.home.compass

import android.content.Context
import android.hardware.*
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentCompassBinding
import com.bizmiz.adepuz.ui.MainActivity
import kotlin.math.roundToInt

class CompassFragment : Fragment() {
    companion object {
        const val QIBLA_LATITUDE = 21.38908
        const val QIBLA_LONGITUDE = 39.85791
    }

    var currentDegree: Float = 0f
    var currentNeedleDegree: Float = 0f
    private lateinit var sensor: Sensor
    lateinit var userLocation: Location
    lateinit var needleAnimation: RotateAnimation
    private lateinit var binding: FragmentCompassBinding
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
        val latitude = requireArguments().getDouble("lat", 0.0)
        val longitude = requireArguments().getDouble("long", 0.0)
        binding = FragmentCompassBinding.bind(
            inflater.inflate(
                R.layout.fragment_compass,
                container,
                false
            )
        )
        if (latitude != 0.0 && longitude != 0.0) {
            locationQIbla(latitude, longitude)
        }
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.popBackStack()
        }
        return binding.root
    }

    private fun locationQIbla(latitude: Double, longitude: Double) {
        userLocation = Location("User Location")
        userLocation.latitude = latitude
        userLocation.longitude = longitude
        val sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = try {
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        } catch (e: Exception) {
            Toast.makeText(
                requireActivity(),
                "Kechirasiz telefoningiz kompassni qo'llab quvvatlamaydi",
                Toast.LENGTH_SHORT
            ).show()
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        }

        sensorManager.registerListener(object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }

            override fun onSensorChanged(sensorEvent: SensorEvent?) {
                val degree: Float = sensorEvent?.values?.get(0)?.roundToInt()?.toFloat()!!
                var head: Float = sensorEvent.values?.get(0)?.roundToInt()?.toFloat()!!
                val destLocation = Location("Destination Location")
                destLocation.latitude = QIBLA_LATITUDE
                destLocation.longitude = QIBLA_LONGITUDE
                var bearTo = userLocation.bearingTo(destLocation)
                val geoField = GeomagneticField(
                    userLocation.latitude.toFloat(),
                    userLocation.longitude.toFloat(),
                    userLocation.altitude.toFloat(),
                    System.currentTimeMillis()
                )
                head -= geoField.declination
                if (bearTo < 0) {
                    bearTo += 360
                }
                var direction = bearTo - head
                if (direction < 0) {
                    direction += 360
                }
                needleAnimation = RotateAnimation(
                    currentNeedleDegree,
                    direction,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
                )
                needleAnimation.fillAfter = true
                needleAnimation.duration = 500
                binding.ivCompass.startAnimation(needleAnimation)
                currentNeedleDegree = direction
                currentDegree = -degree
            }
        }, sensor, SensorManager.SENSOR_DELAY_GAME)
    }
}