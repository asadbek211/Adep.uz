package com.bizmiz.adepuz.ui.home.articles

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
import com.bizmiz.adepuz.adapters.ArticlesAdapter
import com.bizmiz.adepuz.databinding.FragmentArticlesBinding
import com.bizmiz.adepuz.ui.MainActivity
import com.bizmiz.adepuz.ui.home.GetDataViewModel
import com.bizmiz.adepuz.utils.ResourceState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesFragment : Fragment() {
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var binding: FragmentArticlesBinding
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
        articlesAdapter = ArticlesAdapter()
        binding = FragmentArticlesBinding.bind(
            inflater.inflate(
                R.layout.fragment_articles,
                container,
                false
            )
        )
        getDataViewModel.getArticles()
        articlesObserve()
        binding.ivBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.basicNavigation)
            navController.popBackStack()
        }
        binding.articlesRecyclerview.adapter = articlesAdapter
        articlesAdapter.onClickListener { posts ->
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

    private fun articlesObserve() {
        getDataViewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            when (articles.status) {
                ResourceState.SUCCESS -> {
                    if (articles.data != null && articles.data.isNotEmpty()) {
                        binding.loading.visibility = View.GONE
                        articlesAdapter.articlesList = articles.data
                    }
                }
                ResourceState.ERROR -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), articles.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}