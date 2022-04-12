package com.bizmiz.adepuz.ui.postInfo

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.databinding.FragmentPostInfoBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PostInfoFragment : Fragment() {
    private lateinit var binding: FragmentPostInfoBinding
    private var image:String? = null
    private var title:String? = null
    private var description:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        image = requireArguments().getString("image")
        title = requireArguments().getString("title")
        description = requireArguments().getString("description")

    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
      binding = FragmentPostInfoBinding.bind(inflater.inflate(R.layout.fragment_post_info, container, false))
         binding.ivBack.setOnClickListener {
             val navController =
                 Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
             navController.popBackStack()
         }
        binding.ivFavourite.setOnClickListener {
            binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_white)
        }
        Glide.with(this).load("https://adep.uz/$image")
            .into(binding.newsImage)
        binding.tvNewsTitle.text = title
        CoroutineScope(Dispatchers.Main).launch {
            binding.tvNewsDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(description.toString())
            }
        }
        binding.nestedScroll.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
           if(scrollY>550){
               binding.ivFavourite.setBackgroundResource(R.drawable.product_view_button_shape2)
               binding.ivBack.setBackgroundResource(R.drawable.product_view_button_shape2)
           }else{
               binding.ivFavourite.setBackgroundResource(R.drawable.product_view_button_shape)
               binding.ivBack.setBackgroundResource(R.drawable.product_view_button_shape)
           }
        }
        return binding.root
    }
}