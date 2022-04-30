package com.bizmiz.adepuz.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.adapters.NewsAdapter
import com.bizmiz.adepuz.adapters.PostsAdapter
import com.bizmiz.adepuz.databinding.FragmentHomeBinding
import com.bizmiz.adepuz.model.ArticlesData
import com.bizmiz.adepuz.ui.MainActivity
import com.bizmiz.adepuz.utils.Constant
import com.bizmiz.adepuz.utils.ResourceState
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.orhanobut.hawk.Hawk
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class HomeFragment : Fragment() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private val getDataViewModel: GetDataViewModel by viewModel()
    private val namazTimeViewModel: NamazTimeViewModel by viewModel()
    private lateinit var postsAdapter: PostsAdapter
    private var usefulPosition by Delegates.notNull<Int>()
    private lateinit var newsAdapter: NewsAdapter
    private var lat = 0.0
    private var long = 0.0
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = requireActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        getLastLocation()
    }

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
        requireActivity().window.setFlags(
            0,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        if (!::binding.isInitialized) {
            binding =
                FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        }
        postsAdapter = PostsAdapter()
        newsAdapter = NewsAdapter()
        binding.homePostsRecyclerview.adapter = postsAdapter
        binding.homeNewsRecyclerview.adapter = newsAdapter
        getDataViewModel.getArticles()
        getDataViewModel.getNews()
        getDataViewModel.getUsefulPosts()
        articlesObserve()
        newsObserve()
        usefulPostsObserve()
        getDistrictObserve()
        namazTimeObserve()
        newsAdapter.onClickListener { news ->
            val bundle = bundleOf(
                "id" to news.id,
                "views" to news.views,
                "createdDate" to news.created_at,
                "image" to news.image,
                "title" to news.title,
                "description" to news.description
            )
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.navigate(R.id.action_basicFragment_to_postInfoFragment, bundle)
        }
        postsAdapter.onClickListener { news ->
            val bundle = bundleOf(
                "id" to news.id,
                "views" to news.views,
                "createdDate" to news.created_at,
                "image" to news.image,
                "title" to news.title,
                "description" to news.description
            )
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.navigate(R.id.action_basicFragment_to_postInfoFragment, bundle)
        }
        binding.txtLocation.setOnClickListener {
            getLastLocation()
        }
        binding.ivCompass.setOnClickListener {
            val bundle = bundleOf(
                "lat" to lat,
                "long" to long
            )
            val navControl = Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navControl.navigate(R.id.action_basicFragment_to_compassFragment, bundle)
        }
        binding.homeMore.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.basicNavigation)
            navController.navigate(R.id.action_home_to_articlesFragment)
        }
        binding.homePaydaliMore.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.basicNavigation)
            navController.navigate(R.id.action_home_to_usefulFragment)
        }
        when(getLanguage()){
            "uz-rUZ","es"-> binding.imageView.setImageResource(R.drawable.l_new_lotin)
            "ru","en"-> binding.imageView.setImageResource(R.drawable.l_new_krill)
            else -> binding.imageView.setImageResource(R.drawable.l_new_lotin)
        }
        binding.ivTasbih.setOnClickListener {
            val navControl = Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navControl.navigate(R.id.action_basicFragment_to_tasbeehFragment)
        }
        return binding.root
    }

    private fun articlesObserve() {
        getDataViewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            when (articles.status) {
                ResourceState.SUCCESS -> {
                    if (articles.data != null && articles.data.isNotEmpty()) {
                        binding.loading.visibility = View.GONE
                        val list: ArrayList<ArticlesData> = arrayListOf()
                        articles.data.forEach {
                            if (list.size < 4) {
                                list.add(it)
                            }
                        }
                        postsAdapter.postsList = list
                    }


                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), articles.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun newsObserve() {
        getDataViewModel.news.observe(viewLifecycleOwner, Observer { news ->
            when (news.status) {
                ResourceState.SUCCESS -> {
                    if (news.data != null && news.data.isNotEmpty()) {
                        binding.loading.visibility = View.GONE
                    }
                    newsAdapter.newsList = news.data!!
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), news.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun usefulPostsObserve() {
        getDataViewModel.usefulPosts.observe(viewLifecycleOwner, Observer { usefulPosts ->
            when (usefulPosts.status) {
                ResourceState.SUCCESS -> {
                    if (usefulPosts.data != null && usefulPosts.data.isNotEmpty()) {
                        binding.loading.visibility = View.GONE
                    }
                    val imageList: ArrayList<String> = arrayListOf()
                    val titleList: ArrayList<String> = arrayListOf()
                    usefulPosts.data?.forEach {
                        if (imageList.size < 5) {
                            imageList.add(it.image)
                            titleList.add(it.title)
                        }
                    }
                    binding.carouselView.setImageListener { position, imageView ->
                        Glide.with(imageView).load("https://adep.uz/${imageList[position]}")
                            .into(imageView)
                        imageView.setOnClickListener {
                            val data = usefulPosts.data?.get(usefulPosition)
                            val bundle = bundleOf(
                                "id" to data?.id,
                                "views" to data?.views,
                                "createdDate" to data?.created_at,
                                "image" to data?.image,
                                "title" to data?.title,
                                "description" to data?.description
                            )
                            val navController =
                                Navigation.findNavController(
                                    requireActivity(),
                                    R.id.fragmentNavigation
                                )
                            navController.navigate(
                                R.id.action_basicFragment_to_postInfoFragment,
                                bundle
                            )
                        }
                    }
                    binding.carouselView.addOnPageChangeListener(object :
                        ViewPager.OnPageChangeListener {
                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {
                            usefulPosition = position
                            if (titleList.isNotEmpty()) {
                                binding.usefulPostsTitle.text = titleList[position]
                            }
                        }

                        override fun onPageSelected(position: Int) {
                        }

                        override fun onPageScrollStateChanged(state: Int) {

                        }

                    })
                    binding.carouselView.pageCount = imageList.size
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), usefulPosts.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun namazTimeObserve() {
        namazTimeViewModel.namazTimes.observe(viewLifecycleOwner, Observer {
            binding.apply {
                txtTime1.text = it.fajr
                txtTime2.text = it.sunrise
                txtTime3.text = it.dhuhr
                txtTime4.text = it.asr
                txtTime5.text = it.maghrib
                txtTime6.text = it.isha
            }
        })
    }

    private fun networkCheck(): Boolean {
        val conManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo = conManager.activeNetworkInfo
        return internetInfo != null && internetInfo.isConnected
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isGPSEnable()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        newLocationData()
                    } else {
                        lat = location.latitude
                        long = location.longitude
                        val timezone =
                            SimpleDateFormat(
                                "Z",
                                Locale.getDefault()
                            ).format(Calendar.getInstance().time)
                                .toInt() / 100
                        namazTimeViewModel.getTimes(timezone, location.latitude, location.longitude)
                        when {
                            networkCheck() -> {
                                val url =
                                    "${Constant.BASE_URL_LOCATION}format=geocodejson&lat=${location.latitude}&lon=${location.longitude}"
                                getDataViewModel.getDistrict(url)
                            }
                            prefs.contains("location") -> {
                                binding.txtLocation.text = prefs.getString("location", "")
                            }
                            else -> {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.internet_net),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
            } else {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        } else {
            requestPermission()
        }
    }

    private fun newLocationData() {
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            interval = 0
            fastestInterval = 0
            numUpdates = 1
        }
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
            }
        }
        if (checkPermission()) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()!!
            )
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

    private fun Fragment.isGPSEnable(): Boolean =
        requireContext().getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun Context.getLocationManager() =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), 1010
        )
    }

    private fun getDistrictObserve() {
        var state = ""
        var county = ""
        getDataViewModel.district.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    Log.d("geocode", it.data?.body().toString())
                    it.data?.body()?.features?.forEach { feature ->

                        when {
                            feature.properties.geocoding.city != null -> {
                                county = feature.properties.geocoding.city
                            }
                            feature.properties.geocoding.county != null -> {
                                county = feature.properties.geocoding.county
                            }
                            else -> {
                                county = ""
                            }
                        }

                        if (feature.properties.geocoding.state != null) {
                            state = feature.properties.geocoding.state.replace(
                                "Respublikası",
                                ""
                            )
                            county = "$county, "
                        } else {
                            state = ""
                        }
                        binding.txtLocation.text = county + state
                        prefs.edit()
                            .putString("location", county + state)
                            .apply()
                    }
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.txtLocation.text = getString(R.string.net_location)
                }
            }
        })
    }
    private fun getLanguage():String {
        return Hawk.get("pref_lang",getString(R.string.o_zbek_lotin))

    }
}