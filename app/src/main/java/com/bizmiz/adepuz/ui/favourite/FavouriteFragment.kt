package com.bizmiz.adepuz.ui.favourite

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.adapters.FavouriteAdapter
import com.bizmiz.adepuz.databinding.FragmentFavouriteBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.bizmiz.adepuz.ui.home.GetDataViewModel
import com.bizmiz.adepuz.utils.ResourceState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private val getDataViewModel: GetDataViewModel by viewModel()
    private lateinit var favouriteAdapter: FavouriteAdapter
    private lateinit var prefs: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).numberCheck = 2
        requireActivity().window.setFlags(
           0,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        prefs = requireActivity().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE)
        binding = FragmentFavouriteBinding.bind(
            inflater.inflate(
                R.layout.fragment_favourite,
                container,
                false
            )
        )
        getDataViewModel.getPosts(prefs)
        favouriteAdapter = FavouriteAdapter()
        binding.homeNewsRecyclerview.adapter = favouriteAdapter
        postsObserve()
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.basicNavigation)
            navController.popBackStack()
        }
        favouriteAdapter.onClickListener { posts ->
            val bundle = bundleOf(
                "id" to posts.id,
                "views" to posts.views,
                "createdDate" to posts.created_at,
                "image" to posts.image,
                "title" to posts.title,
                "description" to posts.description
            )
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentNavigation)
            navController.navigate(R.id.action_basicFragment_to_postInfoFragment, bundle)
        }
        return binding.root
    }

    private fun postsObserve() {
        getDataViewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            when (posts.status) {
                ResourceState.SUCCESS -> {
                    if (posts.data != null) {
                        if (posts.data.isNotEmpty()) {
                            favouriteAdapter.postsList = posts.data
                            binding.title.visibility = View.GONE
                        } else {
                            binding.title.visibility = View.VISIBLE
                        }
                    }
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), posts.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}