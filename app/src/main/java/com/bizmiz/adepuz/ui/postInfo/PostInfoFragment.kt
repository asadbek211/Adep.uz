package com.bizmiz.adepuz.ui.postInfo

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentPostInfoBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.bizmiz.adepuz.ui.home.GetDataViewModel
import com.bizmiz.adepuz.utils.ResourceState
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import java.text.SimpleDateFormat
import java.time.Instant

class PostInfoFragment : Fragment() {
    private val getDataViewModel: GetDataViewModel by viewModel()
    private lateinit var binding: FragmentPostInfoBinding
    private var postId = -1
    private var views = ""
    private var createData = ""
    private var image = ""
    private var title = ""
    private var description = ""
    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = requireActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE)
        val prefsView = requireActivity().getSharedPreferences("MY_PREFS_VIEW", Context.MODE_PRIVATE)
        postId = requireArguments().getInt("id")
        views = requireArguments().getString("views","")
        createData = requireArguments().getString("createdDate","")
        image = requireArguments().getString("image", "")
        title = requireArguments().getString("title", "")
        description = requireArguments().getString("description", "")
        if (!prefsView.contains(postId.toString())){
            getDataViewModel.updateView(postId,"${views.trim().toInt()+1}")
            prefsView.edit().putInt(postId.toString(),postId).apply()
        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).numberCheck = 3
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = FragmentPostInfoBinding.bind(
            inflater.inflate(
                R.layout.fragment_post_info,
                container,
                false
            )
        )
        if (getPostId(postId)) {
            binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_white)
        } else {
            binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.popBackStack()
        }
        binding.ivFavourite.setOnClickListener {
            if (getPostId(postId)) {
                binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                removePostId(postId)
            } else {
                binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_white)
                setPostId(postId)
            }
        }
        Glide.with(this).load("https://adep.uz/$image")
            .into(binding.newsImage)
        binding.tvNewsTitle.text = title
        binding.eyeCount.text = views
        if (createData.isNotEmpty()){
            val instant = Instant.parse(createData)
            val mills: Long = instant.toEpochMilli()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateString = simpleDateFormat.format(mills)
            binding.publishData.text = String.format("%s", dateString)
        }
        binding.tvNewsDescription.setHtml(
            description,
            HtmlHttpImageGetter(binding.tvNewsDescription)
        )
        binding.nestedScroll.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > 550) {
                binding.ivFavourite.setBackgroundResource(R.drawable.product_view_button_shape2)
                binding.ivBack.setBackgroundResource(R.drawable.product_view_button_shape2)
            } else {
                binding.ivFavourite.setBackgroundResource(R.drawable.product_view_button_shape)
                binding.ivBack.setBackgroundResource(R.drawable.product_view_button_shape)
            }
        }
        updateViewsObserve()
        return binding.root
    }

    private fun setPostId(id: Int) {
        prefs.edit().putInt(id.toString(), id).apply()
    }

    private fun getPostId(id: Int): Boolean {
        return prefs.contains(id.toString())
    }

    private fun removePostId(id: Int) {
        prefs.edit().remove(id.toString()).apply()
    }
    private fun updateViewsObserve() {
        getDataViewModel.updateView.observe(viewLifecycleOwner, Observer {
             when(it.status){
                 ResourceState.SUCCESS->{

                 }
                 ResourceState.ERROR->{
                     Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                 }
             }
        })
    }
}
