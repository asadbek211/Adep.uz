package com.bizmiz.adepuz.ui.home.paydali

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.adepuz.R
import com.bizmiz.adepuz.adapters.UsefulAdapter
import com.bizmiz.adepuz.databinding.FragmentUsefulBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.bizmiz.adepuz.ui.home.GetDataViewModel
import com.bizmiz.adepuz.utils.ResourceState
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsefulFragment : Fragment() {
    private lateinit var binding: FragmentUsefulBinding
    private lateinit var usefulAdapter: UsefulAdapter
    private val getDataViewModel: GetDataViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).numberCheck = 2
        requireActivity().window.setFlags(
            0,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        usefulAdapter = UsefulAdapter()
        binding = FragmentUsefulBinding.bind(inflater.inflate(R.layout.fragment_useful, container, false))
        getDataViewModel.getUsefulPosts()
        usefulObserve()
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.basicNavigation)
            navController.popBackStack()
        }
        binding.usefulRecyclerview.adapter = usefulAdapter
        usefulAdapter.onClickListener { posts ->
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
    private fun usefulObserve() {
        getDataViewModel.usefulPosts.observe(viewLifecycleOwner, Observer { useful ->
            when (useful.status) {
                ResourceState.SUCCESS -> {
                    if (useful.data != null && useful.data.isNotEmpty()) {
                        binding.loading.visibility = View.GONE
                        usefulAdapter.usefulList = useful.data
                    }
                }
                ResourceState.ERROR -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), useful.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}